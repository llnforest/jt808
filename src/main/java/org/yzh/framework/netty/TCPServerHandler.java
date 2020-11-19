package org.yzh.framework.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.MsgUtils;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.t808.T0001;
import org.yzh.protocol.t808.T8100;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        if (!(msg instanceof AbstractMessage))
            return;
        log.info("收到消息：{}",msg);
        AbstractMessage request = (AbstractMessage) msg;
        log.info("收到》》{}",request);
        AbstractMessage response;
        Channel channel = ctx.channel();
        Session session = channel.attr(Session.KEY).get();
//        request.setSession(session);
        long time = session.access();

        try {
            Handler handler = handlerMapping.getHandler(request.getMessageId());
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

            ctx.writeAndFlush(response);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Session session = sessionManager.newSession(channel);
        channel.attr(Session.KEY).set(session);
        // 检查消息，触发重发机制
        MsgUtils.checkMsg(ctx);
        log.info(">>>>>终端连接{}", session);
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