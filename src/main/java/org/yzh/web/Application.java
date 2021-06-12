package org.yzh.web;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.TcpServerUtils;
import org.yzh.framework.netty.websocket.WebSocketChannelHandlerPool;
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
                String phone = "17299841738";
                switch (i) {
                    case 0:
                        JT808Beans.T01F0();
                        break;
                    case 1:
                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T01F1(),phone,1));
                        break;
                    case 2:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_time_up_command(),phone));
                        break;
                    case 3:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_photo_command(),phone));
                        break;
                    case 4:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_photo_search_command(),phone));
                        break;
                    case 5:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_photo_up_only(),phone));
                        break;
                    case 6:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_terminal_set(),phone));
                        break;
                    case 7:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_terminal_status(),phone));
                        break;
                    case 8:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.t8900_0900_terminal_param_search(),phone));
                        break;
                    case 9:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8103(),phone));
                        break;
                    case 10:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8104(),phone));

                        break;
                    case 11:
                        TcpClientUtils.getCtx().writeAndFlush(JT808Beans.H2019(JT808Beans.T8000(),phone,1));
                        break;
                    case 12:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8105(),phone));
                        break;
                    case 13:
//                        TcpServerUtils.getClient().writeObject(JT808Beans.H2019(JT808Beans.T8202(),phone));
                        break;
                    case 14:
                        log.info("发送数据");
                        WebSocketChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame("你个大傻叉"));
                        break;
                }
            }
        }
    }
}