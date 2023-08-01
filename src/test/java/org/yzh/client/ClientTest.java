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


//    private static final int port;

    //7611
    static {
        ClientConfig jtConfig = new ClientConfig.Builder()
//                .setIp("127.0.0.1")
                .setIp("36.155.130.31")
                .setPort(8885)
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
            System.out.println("选择发送的消息：0.心跳 1.注册 2.位置信息上报 3.注销 4.上行 5.上报学员登录 6.终端鉴权 7.上报教练员登录 8.教练登出 9.学员登出 10.上报学时记录 11.上报照片查询结果 12.照片上传初始化 13.上传照片数据包 14.平台登录  15.平台退出");
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
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T0200()));//位置上报
                        break;
                    case 3:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T0003()));//注销
                        break;
                    case 4:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900()));//上行
                        break;
                    case 5:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_student_login()));//上报学员登录
                        break;
                    case 6:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T0102_2013()));//终端鉴权
                        break;
                    case 7:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_coach_login()));//上报教练员登录
                        break;
                    case 8:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_coach_logout()));//上报教练员登出
                        break;
                    case 9:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_student_logout()));//上报学员登出
                        break;
                    case 10:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_time_up()));//上报学时记录
                        break;
                    case 11:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_photo_search_result_up()));//上报照片查询结果
                        break;
                    case 12:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_photo_up_init()));//照片上传初始化
                        break;
                    case 13:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T8900_0900_photo_up_data()));//上传照片数据包
                        break;
                    case 14:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T01F0()));//上传平台登录
                        break;
                    case 15:
                        tcpClient.writeObject(JT808Beans.H2019(JT808Beans.T01F1()));//上传平台登出
                        break;
                }
            }
        }
    }
}