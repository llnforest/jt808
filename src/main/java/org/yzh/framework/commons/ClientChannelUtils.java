package org.yzh.framework.commons;

import io.netty.channel.ChannelHandlerContext;
import org.yzh.framework.netty.client.TCPClient;

public class ClientChannelUtils {
    private static ChannelHandlerContext ctx;
    private static TCPClient client;

    public static void setCtx(ChannelHandlerContext ctx1){
        ctx = ctx1;
    }

    public static ChannelHandlerContext getCtx(){
        return ctx;
    }

    public static void setClient(TCPClient tcpClient){
        client = tcpClient;
    }

    public static  TCPClient getClient(){
        return client;
    }
}
