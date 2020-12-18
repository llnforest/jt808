package org.yzh.web.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.yzh.framework.netty.websocket.WebSocketServer;


import java.util.Date;

@Configuration
@ConditionalOnProperty(value = "websocket.server.enable", havingValue = "true")
public class WebSocketConfig implements InitializingBean, DisposableBean {

    @Value("${websocket.server.port}")
    private int port;

    @Autowired
    private WebSocketServer webSocketServer;

    @Bean
    public WebSocketServer webSocketServer() {
        System.out.println("----------websocket服务------------");
        System.out.println(new Date().getTime());
        return new WebSocketServer(port);
    }


    @Override
    public void afterPropertiesSet() {
        webSocketServer.start();
    }

    @Override
    public void destroy() {
        webSocketServer.stop();
    }
}