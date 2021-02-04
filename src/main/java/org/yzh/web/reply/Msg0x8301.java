package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 立即拍照
 */
public class Msg0x8301 {
    public static Map<String, Channel> msg_0x8301 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8301.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8301.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8301.remove(phone);
    }
}
