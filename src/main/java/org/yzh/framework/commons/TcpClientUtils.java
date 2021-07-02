package org.yzh.framework.commons;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.web.model.ResponseModel;
import org.yzh.web.model.entity.JsDevice;
import org.yzh.web.model.vo.DeviceInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TcpClientUtils {
    private static final Logger log = LoggerFactory.getLogger(TcpClientUtils.class);

    public static Map<String, Channel> clientMap = new HashMap<>();

    public static Map<String, DeviceInfo> clientDeviceMap = new HashMap<>();

    private static ChannelHandlerContext ctx;

    public static void setCtx(ChannelHandlerContext ctx1){
        ctx = ctx1;
    }

    public static ChannelHandlerContext getCtx(){
        return ctx;
    }

    public  static void setClientDevice(String phone, DeviceInfo deviceInfo){
        clientDeviceMap.put(phone,deviceInfo);

    }

    public static DeviceInfo getClientDevice(String phone){
        try {
            if(clientDeviceMap.containsKey(phone)){
                return clientDeviceMap.get(phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static void setClientChannel(String phone, Channel cx){
        clientMap.put(phone,cx);
        log.info("clientMap:{}",clientMap);

    }

    public static Channel getClientChannel(String phone){
        try {
            log.info("channelMap:{}",clientMap);
            if(clientMap.containsKey(phone)){
                return clientMap.get(phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removeClientChannel(String phone){
        clientMap.remove(phone);
        clientDeviceMap.remove(phone);
    }

    public static void write(Channel channel, Object model){
        if(channel != null && model != null){
            log.info("send to client:{}", model);
            channel.writeAndFlush(model);
        }
    }

}
