package org.yzh.web;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yzh.framework.commons.ClientChannelUtils;
import org.yzh.framework.netty.websocket.WebSocketChannelHandlerPool;
import org.yzh.framework.netty.websocket.WebSocketServerHandler;
import org.yzh.web.protocol.JT808Beans;

import java.util.Scanner;

@SpringBootApplication
public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("***Spring 启动成功***");
        System.out.println("选择发送的消息：0.平台登录 1.平台登出 2.命令上报学时记录 3.立即拍照 4.查询照片 5.上传指定照片 6.设置计时终端应用参数 7.设置禁训状态 8.查询计时终端应用参数 9.设置终端参数 10.查询终端参数 11.查询指定终端参数 12.终端控制 13.临时位置跟踪控制");
        while (true) {
            while (scanner.hasNext()) {
                int i = scanner.nextInt();
                switch (i) {
                    case 0:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T0090()));
                        break;
                    case 1:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T0091()));
                        break;
                    case 2:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_time_up_command()));
                        break;
                    case 3:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_photo_command()));
                        break;
                    case 4:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_photo_search_command()));
                        break;
                    case 5:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_photo_up_only()));
                        break;
                    case 6:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_terminal_set()));
                        break;
                    case 7:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_terminal_status()));
                        break;
                    case 8:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_terminal_param_search()));
                        break;
                    case 9:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8103()));
                        break;
                    case 10:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8104()));
                        break;
                    case 11:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8106()));
                        break;
                    case 12:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8105()));
                        break;
                    case 13:
                        ClientChannelUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8202()));
                        break;
                    case 14:
                        log.info("发送数据");
                        log.info("channelGroup:{}",WebSocketChannelHandlerPool.channelGroup);
                        WebSocketChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame("你个大傻叉"));
                        break;
                }
            }
        }
    }
}