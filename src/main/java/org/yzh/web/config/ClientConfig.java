package org.yzh.web.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.commons.Const;
import org.yzh.framework.commons.TcpServerUtils;
import org.yzh.framework.netty.client.TCPClient;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

import java.util.Scanner;

@Configuration
@ConditionalOnProperty(value = "tcp.client.enable", havingValue = "true")
public class ClientConfig implements InitializingBean, DisposableBean {
//    private static final Scanner scanner = new Scanner(System.in);
    @Value("${tcp.client.port}")
    private int port;

    @Value("${tcp.client.ip}")
    private String ip;

    @Autowired
    private TCPClient jt808Client;


    @Bean
    public TCPClient jt808Client() {
        org.yzh.framework.netty.client.ClientConfig jtConfig = new org.yzh.framework.netty.client.ClientConfig.Builder()
                .setIp(ip)
                .setPort(port)
                .setMaxFrameLength(1024)
                .setDelimiters(Const.delimiter)
                .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                .setHandlerMapping(new org.yzh.framework.netty.client.HandlerMapping("org.yzh.web.endpoint"))
                .build();
        System.out.println("----------808客户端服务------------");
        return new TCPClient(jtConfig);
    }

    @Override
    public void afterPropertiesSet() {
        TCPClient tcpClient = jt808Client.start();
        TcpServerUtils.setClient(jt808Client);
    }
    @Override
    public void destroy() {
        jt808Client.stop();
    }
}