package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询终端参数
 */
public class Msg0x8104 {
    public static Map<String, Channel> msg_0x8104 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8104.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8104.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8104.remove(phone);
    }
}
