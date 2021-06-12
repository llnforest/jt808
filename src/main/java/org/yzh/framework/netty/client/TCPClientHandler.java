package org.yzh.framework.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@ChannelHandler.Sharable
public class TCPClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPClientHandler.class.getSimpleName());

    private HandlerMapping handlerMapping;


    public TCPClientHandler(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("msg:{}",msg);
        if (!(msg instanceof AbstractMessage))
            return;
        AbstractMessage request = (AbstractMessage) msg;
        log.info(">>>>>>>>>>收到消息:{}", request);
        Channel channel = ctx.channel();

        try {
            AbstractHeader header = request.getHeader();
            log.info("haddleMapping:{}",handlerMapping);
            Handler handler = handlerMapping.getHandler(((AbstractMessage) msg).getMarkId());
            log.info("handler:{}",handler);
            AbstractMessage messageResponse = handler.invoke(request);


            if (messageResponse != null) {
                channel.writeAndFlush(messageResponse);
                log.info("<<<<<<<<<<返回消息:{}", messageResponse);

            }

        } catch (Exception e) {
            log.warn(String.valueOf(request), e);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(">>>>>连接到服务端成功{}", ctx.channel().remoteAddress());
//        TcpServerUtils.setCtx(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("<<<<<断开连接");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error("<<<<<发生异常", e);
        super.exceptionCaught(ctx, e);
    }
}