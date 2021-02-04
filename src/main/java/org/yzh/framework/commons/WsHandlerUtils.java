package org.yzh.framework.commons;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.netty.websocket.WebSocketServer;
import org.yzh.web.endpoint.WsEndpoint;
import org.yzh.web.model.ResponseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WsHandlerUtils {
    private static final Logger log = LoggerFactory.getLogger(WsHandlerUtils.class);

    public static Map<String,Method> methodMap = new HashMap<>();
    public static Map<Integer,Channel> wsChannelMap = new HashMap<>();

    public  static void setMethodMap(){
        Method[] methods = WsEndpoint.class.getDeclaredMethods();
        for(Method method:methods){
            methodMap.put(method.getName(),method);
        }

    }

    public static ResponseModel invokeMethod(String msgId, Map<String,Object> map, Channel channel){
        try {
            String methodName = "_" + msgId;
            if(methodMap.containsKey(methodName)){
                //判断是否鉴权
                if(!msgId.equals("0x0102") && !wsChannelMap.containsKey(channel.id().hashCode())){
                    return new ResponseModel("1","未鉴权客户端");
                }
                log.info("method:{},{}",methodName,methodMap.get(methodName));
                return (ResponseModel) methodMap.get(methodName).invoke(WsEndpoint.class.newInstance(),map,channel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static void setWsChannelMap(Channel channel){
        int channelId = channel.id().hashCode();
        wsChannelMap.put(channelId,channel);
    }

    public  static Map<Integer,Channel> getWsChannelMap(){
        return wsChannelMap;
    }

    public  static Channel getWsChannelMap(int channelId){
        return wsChannelMap.get(channelId);
    }

    public static void write(Channel channel,ResponseModel model){
        if(channel != null && model != null){
            log.info("send to ws:{}",JSONObject.fromObject(model).toString());
            channel.writeAndFlush(new TextWebSocketFrame(JSONObject.fromObject(model).toString()));
        }
    }

}
