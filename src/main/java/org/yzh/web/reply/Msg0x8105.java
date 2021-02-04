package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 终端控制
 */
public class Msg0x8105 {
    public static Map<String, Channel> msg_0x8105 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8105.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8105.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8105.remove(phone);
    }
}
