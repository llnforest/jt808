package org.yzh.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yzh.framework.commons.ClientChannelUtils;
import org.yzh.web.protocol.JT808Beans;

import java.util.Scanner;

@SpringBootApplication
public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("***Spring 启动成功***");
        while (true) {
            System.out.println("选择发送的消息：0.平台登录 1.平台登出");
            while (scanner.hasNext()) {
                int i = scanner.nextInt();
                switch (i) {
                    case 0:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T0090()));
                        break;
                    case 1:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T0091()));
                        break;
                }
            }
        }
    }
}