package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 临时位置跟踪控制
 */
public class Msg0x8202 {
    public static Map<String, Channel> msg_0x8202 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8202.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8202.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8202.remove(phone);
    }
}
