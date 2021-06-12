package org.yzh.framework.commons;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.yzh.framework.netty.TCPServer;
import org.yzh.framework.netty.client.TCPClient;

public class TcpServerUtils {
    private static TCPClient client;


    public static void setClient(TCPClient client1){
        client = client1;
    }

    public static  TCPClient getClient(){
        return client;
    }
}
