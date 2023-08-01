package org.yzh.framework.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.commons.CommonUtils;
import org.yzh.framework.commons.Const;
import org.yzh.framework.commons.TcpServerUtils;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.protocol.JT808Beans;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@ChannelHandler.Sharable
public class TCPClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPClientHandler.class.getSimpleName());

    private HandlerMapping handlerMapping;

    private TCPClient tcpClient;

    private MessageEncoder encoder = new JTMessageEncoder("org.yzh.protocol");

    public TCPClientHandler(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Value("${tcp.client.port}")
    private static int port;

    @Value("${tcp.client.ip}")
    private static String ip;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("msg:{}", msg);
        if (!(msg instanceof AbstractMessage))
            return;
        AbstractMessage request = (AbstractMessage) msg;
        log.info(">>>>>>>>>>收到消息:{}", request);
        Channel channel = ctx.channel();

        try {
            AbstractHeader header = request.getHeader();
            log.info("haddleMapping:{}", handlerMapping);
            Handler handler = handlerMapping.getHandler(((AbstractMessage) msg).getMarkId());
            log.info("handler:{}", handler);
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
        //断开重连
        Thread.sleep(15000);
        TCPClient jt808Client = new TCPClient(
                new org.yzh.framework.netty.client.ClientConfig.Builder()
                        .setIp(ip)
                        .setPort(port)
                        .setMaxFrameLength(1024)
                        .setDelimiters(Const.delimiter)
                        .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                        .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                        .setHandlerMapping(new org.yzh.framework.netty.client.HandlerMapping("org.yzh.web.endpoint"))
                        .build());
        System.out.println("----------808客户端服务------------");

        TCPClient tcpClient = jt808Client.start();
        TcpServerUtils.setClient(jt808Client);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error("<<<<<发生异常", e);
        super.exceptionCaught(ctx, e);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent && Const.isHeartBeat) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                AbstractMessage message = JT808Beans.H2019(JT808Beans.T0002(), Const.phone, 1);
                ByteBuf byteBuf = encoder.encode((AbstractMessage) message);
                ByteBuf buf = Unpooled.buffer(1024);
                buf.writeBytes(Const.delimiter).writeBytes(byteBuf).writeBytes(Const.delimiter);
                byte[] newMessage = new byte[buf.readableBytes()];
                buf.readBytes(newMessage);

                ctx.writeAndFlush(Unpooled.copiedBuffer(newMessage));
                log.info("<<<<<<<<<<发送报文:{}", CommonUtils.getHexString(newMessage));
                log.info("<<<<<<<<<<发送消息:{}", message);
            }
        }
    }
}