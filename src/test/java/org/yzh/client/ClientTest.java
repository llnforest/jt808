package org.yzh.client;

import org.yzh.framework.netty.client.ClientConfig;
import org.yzh.framework.netty.client.HandlerMapping;
import org.yzh.framework.netty.client.TCPClient;
import org.yzh.protocol.JT808Beans;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

import java.util.Scanner;

/**
 * 不依赖spring，快速启动netty服务
 */
public class ClientTest {

    private static final Scanner scanner = new Scanner(System.in);

    private static TCPClient tcpClient;

    //7611
    static {
        ClientConfig jtConfig = new ClientConfig.Builder()
                .setIp("127.0.0.1")
                .setPort(7611)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[]{0x7e})
                .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                .setHandlerMapping(new HandlerMapping("org.yzh.client"))
                .build();

        tcpClient = new TCPClient(jtConfig).start();
    }

    public static void main(String[] args) {
        System.out.println(tcpClient);
        while (true) {
            System.out.println("选择发送的消息：0.心跳 1.注册 2.位置信息上报 3.注销 4.上行 5.上报教练登录");
            while (scanner.hasNext()) {
                int i = scanner.nextInt();
                switch (i) {
                    case -1:
                        tcpClient.stop();
                        return;
                    case 0:
                        tcpClient.writeObject((JT808Beans.H2019(JT808Beans.T0002())));//心跳
                        break;
                    case 1:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T0100()));//注册
                        break;
                    case 2:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T0200Attributes()));//位置上报
                        break;
                    case 3:
                        tcpClient.writeObject("1111");//心跳
                        break;
                    case 4:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900()));//上行
                        break;
                    case 5:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_student_login()));//上报学员登录
                        break;
                }
            }
        }
    }
}