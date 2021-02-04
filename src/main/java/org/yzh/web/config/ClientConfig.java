package org.yzh.web.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.commons.ClientChannelUtils;
import org.yzh.framework.netty.client.TCPClient;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

import java.util.Scanner;

@Configuration
@ConditionalOnProperty(value = "tpc-server.jt808.client", havingValue = "true")
public class ClientConfig implements InitializingBean, DisposableBean {
    private static final Scanner scanner = new Scanner(System.in);

    @Autowired
    private TCPClient jt808Client;

    @Bean
    public TCPClient jt808Client() {
        org.yzh.framework.netty.client.ClientConfig jtConfig = new org.yzh.framework.netty.client.ClientConfig.Builder()
                .setIp("127.0.0.1")
                .setPort(7615)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[]{0x7e})
                .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                .setHandlerMapping(new org.yzh.framework.netty.client.HandlerMapping("org.yzh.client"))
                .build();
        System.out.println("----------808客户端服务------------");
        return new TCPClient(jtConfig);
    }

    @Override
    public void afterPropertiesSet() {
        TCPClient tcpClient = jt808Client.start();
        ClientChannelUtils.setClient(jt808Client);
    }
    @Override
    public void destroy() {
        jt808Client.stop();
    }
}