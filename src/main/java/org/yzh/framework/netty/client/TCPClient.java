package org.yzh.framework.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.CommonUtils;
import org.yzh.framework.commons.transform.ByteBufUtils;
import org.yzh.framework.orm.model.AbstractMessage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class TCPClient {

    private static final Logger log = LoggerFactory.getLogger(TCPClient.class);

    private ClientConfig config;

    private EventLoopGroup workerGroup;

    private Channel channel;

    public TCPClient(ClientConfig config) {
        this.config = config;
    }

    private void startInternal() {
        try {
            this.workerGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(workerGroup);
            bootstrap.option(NioChannelOption.SO_REUSEADDR, true)
                    .option(NioChannelOption.TCP_NODELAY, true)
                    .option(NioChannelOption.SO_KEEPALIVE, true)
//                    .option(NioChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(NioSocketChannel ch) {
                            ch.pipeline()
                                    .addLast("frameDecoder", new DelimiterBasedFrameDecoder(config.maxFrameLength,
                                            Unpooled.wrappedBuffer(config.delimiter),
                                            Unpooled.wrappedBuffer(config.delimiter, config.delimiter)))
                                    .addLast("decoder", new ByteToMessageDecoder() {
                                        @Override
                                        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> out) {
                                            AbstractMessage message = config.decoder.decode(buf);
                                            if (message != null)
                                                out.add(message);
                                            buf.skipBytes(buf.readableBytes());
                                        }
                                    })
//                                    .addLast("encoder", new MessageToByteEncoder<AbstractMessage>() {
//                                        @Override
//                                        protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) {
//                                            log.info("发送前:{}",msg);
//                                            ByteBuf buf = config.encoder.encode(msg);
//                                            log.info("<<<<<发送报文[ip={}],hex={}", ctx.channel().remoteAddress(), ByteBufUtil.hexDump(buf));
//                                            out.writeBytes(config.delimiter).writeBytes(buf).writeBytes(config.delimiter);
//                                        }
//                                    })
                                    .addLast("encoder",new ObjectEncoder())
                                    .addLast(new IdleStateHandler(0,5,0, TimeUnit.SECONDS))
                                    .addLast("adapter", config.adapter);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(config.ip, config.port).sync();
            this.channel = channelFuture.channel();
            this.channel.closeFuture();
        } catch (Exception e) {
            log.error("===TCP Client异常关闭", e);
        }
    }

    public void writeObject(Object message) {
        byte[] newMessage;
        if(message instanceof byte[]){
            newMessage = new byte[((byte[]) message).length+config.delimiter.length*2];
            System.arraycopy(config.delimiter,0,newMessage,0,config.delimiter.length);
            System.arraycopy(message,0,newMessage,config.delimiter.length,((byte[]) message).length);
            System.arraycopy(config.delimiter,0,newMessage,config.delimiter.length + ((byte[]) message).length,config.delimiter.length);
        }else{
            ByteBuf byteBuf = config.encoder.encode((AbstractMessage) message);
            ByteBuf buf = Unpooled.buffer(1024);
            buf.writeBytes(config.delimiter).writeBytes(byteBuf).writeBytes(config.delimiter);
            newMessage = new byte[buf.readableBytes()];
            buf.readBytes(newMessage);
        }
        channel.writeAndFlush(Unpooled.copiedBuffer(newMessage));
        log.info("<<<<<<<<<<发送报文:{}", CommonUtils.getHexString(newMessage));
        log.info("<<<<<<<<<<发送消息:{}", message);
    }




    public synchronized TCPClient start() {
        startInternal();
        log.warn("===TCP Client启动成功, port={}===", config.port);
        return this;
    }

    public synchronized void stop() {
        workerGroup.shutdownGracefully();
        log.warn("===TCP Client已经停止, port={}===", config.port);
    }
}