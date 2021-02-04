package org.yzh.framework.commons;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.web.model.ResponseModel;

import java.util.HashMap;
import java.util.Map;

public class TcpClientUtils {
    private static final Logger log = LoggerFactory.getLogger(TcpClientUtils.class);

    public static Map<String, Channel> clientMap = new HashMap<>();

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

    public static void write(Channel channel, Object model){
        if(channel != null && model != null){
            log.info("send to client:{}", model);
            channel.writeAndFlush(model);
        }
    }

}
