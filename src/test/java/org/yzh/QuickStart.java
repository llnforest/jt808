package org.yzh;

import org.yzh.framework.mvc.DefaultHandlerMapping;
import org.yzh.framework.netty.NettyConfig;
import org.yzh.framework.netty.TCPServer;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTMultiPacketListener;

/**
 * 不依赖spring，快速启动netty服务
 */
public class QuickStart {

    public static void main(String[] args) {
        NettyConfig jtConfig = new NettyConfig.Builder()
                .setPort(7615)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[][]{{0x7e}})
                .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                .setHandlerMapping(new DefaultHandlerMapping("org.yzh.server"))
                .setHandlerInterceptor(new JTHandlerInterceptor())
                .setMultiPacketListener(new JTMultiPacketListener(5))
                .setSessionManager(new SessionManager())
                .build();

        TCPServer tcpServer = new TCPServer("808测试服务端开启", jtConfig);
        tcpServer.start();
    }
}