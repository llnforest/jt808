package org.yzh.framework.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.Const;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.TcpServerUtils;
import org.yzh.framework.commons.MsgUtils;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.commons.JT808;

import java.util.Arrays;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPServerHandler.class.getSimpleName());

    private HandlerMapping handlerMapping;

    private HandlerInterceptor interceptor;

    private SessionManager sessionManager;


    public TCPServerHandler(HandlerMapping handlerMapping, HandlerInterceptor interceptor, SessionManager sessionManager) {
        this.handlerMapping = handlerMapping;
        this.interceptor = interceptor;
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("原始消息：{}",msg);
        if (!(msg instanceof AbstractMessage))
            return;
        AbstractMessage request = (AbstractMessage) msg;
        log.info("收到》》{}",request);

        AbstractMessage response;
        Channel channel = ctx.channel();
        Session session = channel.attr(Session.KEY).get();

        long time = session.access();
        try {
            Handler handler;
            //终端心跳、终端注册、终端鉴权
            //先判断终端是否鉴权
            if(Const.isAuth && Arrays.binarySearch(new int[]{1,3,256,258},request.getMessageId()) < 0 && session.getPhone().isEmpty()){
                //通用回答失败
                handler = handlerMapping.getHandler(JT808.未鉴权通用应答);
                response = handler.invoke(request, session);
                ctx.writeAndFlush(response);
                return;
            }

            handler = handlerMapping.getHandler(((AbstractMessage) msg).getMarkId());
            log.info("handler:{}",handler);
            if (handler != null) {
                if (!interceptor.beforeHandle(request, session))
                    return;

                response = handler.invoke(request, session);
                if (handler.returnVoid) {
                    response = interceptor.successful(request, session);
                } else {
                    interceptor.afterHandle(request, response, session);
                }
            } else {
                response = interceptor.notSupported(request, session);
            }
        } catch (Exception e) {
            log.warn(String.valueOf(request), e);
            response = interceptor.exceptional(request, session, e);
        }

        long nowTime = System.currentTimeMillis();
        time = nowTime - time;
        if (time > 200){
            log.info("=========消息ID{},处理耗时{}ms,", Integer.toHexString(request.getMessageId()), time);
        }

////            接收消息消除map
//        if(request.getClass().getName().equals("org.yzh.protocol.t808.T0001")){//终端通用应答
//            T0001 req = (T0001) request;
//            String req_key = String.valueOf(req.getSerialNo());
//            MsgUtils.delMsg(req_key,session);
//        }

        if (response != null){
//            发送消息入map
//            String key = String.valueOf(response.getHeader().getSerialNo());
//            MsgUtils.addMsg(key,response,session);

            //判断是否需要分包发送
            log.info("response:{}",response);
//            MessageEncoder encoder;
//            ByteBuf buf = encoder.encode(response);

            int version = response.getHeader().getVersionNo();
            BeanMetadata bodyMetadata = MessageHelper.getBeanMetadata(response.getClass(), version);
            ByteBuf bodyBuf;
            if (bodyMetadata != null) {
                bodyBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(bodyMetadata.getLength(), 5*1024*1024);
                bodyMetadata.encode(bodyBuf, response);
            } else {
                bodyBuf = Unpooled.EMPTY_BUFFER;
                log.info("未找到对应的BeanMetadata[{}]", response.getClass());
            }

            int totalLength = bodyBuf.readableBytes();

            int length = 1000;
//            int totalLength = buf.readableBytes();
            log.info("总长度：{}",totalLength);
            if(totalLength > length + 20){
                //需要分包
                int packageTotal = (int)Math.ceil((double) totalLength/length);
                response.getHeader().setSubpackage(true);
                response.getHeader().setPackageTotal(packageTotal);
                for(int packageNo = 1;packageNo <= packageTotal;packageNo ++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    response.getHeader().setPackageNo(packageNo);
                    int bodyLength = (totalLength - length*(packageNo-1))>length?length:(totalLength - length*(packageNo-1));
                    response.getHeader().setBodyLength(bodyLength);
                    log.info("第{}包",packageNo);
                    ctx.writeAndFlush(response);
                }
            }else{
                //不需要分包
                ctx.writeAndFlush(response);
            }

            //转发消息
            log.info("msgId:{}",request.getMarkId());
            TcpServerUtils.replayMsg(request,response);


        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Session session = sessionManager.newSession(channel);
        channel.attr(Session.KEY).set(session);
        // 检查消息，触发重发机制
        MsgUtils.checkMsg(ctx);
        log.info("channel:{}",session.getChannel());
        log.info(">>>>>终端连接{}", session);
        TcpClientUtils.setCtx(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Session session = ctx.channel().attr(Session.KEY).get();

        session.invalidate();
        log.info("<<<<<断开连接{}", session);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        Session session = ctx.channel().attr(Session.KEY).get();
        session.invalidate();
        log.info("<<<<<发生异常" + session, e);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        log.info("超时未收到消息了！");
        //主动断开消息
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            IdleState state = event.state();
//            if (state == IdleState.READER_IDLE || state == IdleState.WRITER_IDLE) {
//                Session session = ctx.channel().attr(Session.KEY).get();
//                log.warn("<<<<<主动断开连接{}", session);
//                ctx.close();
//                ctx.executor().shutdownGracefully();
//            }
//        }
    }
}