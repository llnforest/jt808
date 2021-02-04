package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 位置信息查询
 */
public class Msg0x8201 {
    public static Map<String, Channel> msg_0x8201 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8201.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8201.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8201.remove(phone);
    }
}
