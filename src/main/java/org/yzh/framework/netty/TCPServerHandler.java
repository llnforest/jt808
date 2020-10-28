package org.yzh.framework.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private int retransTimes = 3;//总共重传3次

    private int retransTime = 10;//重传秒数


    private Map<Integer, SendMsg> map = new HashMap<>();
    class SendMsg{
        public int serialNo;
        public int times;
        public long time;
        public AbstractMessage data;

    }

    public TCPServerHandler(HandlerMapping handlerMapping, HandlerInterceptor interceptor, SessionManager sessionManager) {
        this.handlerMapping = handlerMapping;
        this.interceptor = interceptor;
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof AbstractMessage))
            return;
        AbstractMessage request = (AbstractMessage) msg;
        AbstractMessage response;
        Channel channel = ctx.channel();
        Session session = channel.attr(Session.KEY).get();
        request.setSession(session);
        long time = session.access();

        try {
            Handler handler = handlerMapping.getHandler(request.getMessageId());
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

        if(request.getClass().getName().equals("org.yzh.protocol.t808.T0001")){//终端通用应答
//            接收消息消除map
            T0001 req = (T0001) request;
            int req_key = req.getSerialNo();
            if(map.containsKey(req_key)){
                map.remove(req_key);
            }
        }

        if (response != null){
//            发送消息入map
            System.out.println(response.getClass().getName());
            int key = response.getHeader().getSerialNo();
            SendMsg sendMsg = new SendMsg();
            sendMsg.times = 0;
            sendMsg.data = response;
            sendMsg.serialNo = key;
            sendMsg.time = nowTime;
            map.put(sendMsg.serialNo,sendMsg);

            ctx.writeAndFlush(response);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Session session = sessionManager.newSession(channel);
        channel.attr(Session.KEY).set(session);
        log.info("-----------active------------");
        // 每隔三秒重发一次消息
        ctx.executor().scheduleAtFixedRate(() -> {
            log.info("1s定时器");
            if (map.size() > 0) {
                System.out.println(map);
                map.forEach((k, v) -> {
                    if(System.currentTimeMillis() - v.time >= 0){
                        v.times = v.times + 1;
                        log.info("定时第"+v.times+"次发送");
                        if (v.times <= this.retransTimes) {
                            ctx.writeAndFlush(v.data);
                            v.time = v.time + this.retransTime*v.times*1000;
                            // 更新未发送三次的。
                            map.put(v.serialNo, v);
                        }else{
                            log.info("超过"+this.retransTimes+"次发送未收到回复，该条信息不再发送");
                            map.remove(v.serialNo);
                        }
                    }

                });
            }
        }, 1, 1, TimeUnit.SECONDS);
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
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();
            if (state == IdleState.READER_IDLE || state == IdleState.WRITER_IDLE) {
                Session session = ctx.channel().attr(Session.KEY).get();
                log.warn("<<<<<主动断开连接{}", session);
                ctx.close();
                ctx.executor().shutdownGracefully();
            }
        }
    }
}