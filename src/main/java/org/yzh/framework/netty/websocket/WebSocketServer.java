package org.yzh.framework.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.codec.DelimiterBasedFrameDecoder;
import org.yzh.framework.codec.LengthFieldAndDelimiterFrameDecoder;
import org.yzh.framework.codec.MessageDecoderWrapper;
import org.yzh.framework.codec.MessageEncoderWrapper;
import org.yzh.framework.netty.NettyConfig;

import java.util.concurrent.TimeUnit;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    private int port;


    public WebSocketServer(int port) {
        this.port = port;
    }

    private void startInternal() {
        try {
            this.bossGroup = new NioEventLoopGroup(1);
            this.workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.option(NioChannelOption.SO_BACKLOG, 1024)
                    .option(NioChannelOption.SO_REUSEADDR, true)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("收到新连接");
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            ch.pipeline().addLast(new WebSocketServerHandler());
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind().sync();
            log.warn("===websocket启动成功, port={}===", port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.warn("==={}出现异常, port={}===", e);
        } finally {
            stop();
        }
    }

    public synchronized void start() {
        if (this.isRunning) {
            log.warn("===websocket已经启动, port={}===",  port);
            return;
        }
        this.isRunning = true;

        new Thread(() -> startInternal()).start();
    }

    public synchronized void stop() {
        if (!this.isRunning) {
            log.warn("===websocket已经停止, port={}===", port);
        }
        this.isRunning = false;

        Future future = this.bossGroup.shutdownGracefully();
        if (!future.isSuccess())
            log.warn("bossGroup 无法正常停止", future.cause());

        future = this.workerGroup.shutdownGracefully();
        if (!future.isSuccess())
            log.warn("workerGroup 无法正常停止", future.cause());

        log.warn("===websocket已经停止, port={}===", port);
    }
}