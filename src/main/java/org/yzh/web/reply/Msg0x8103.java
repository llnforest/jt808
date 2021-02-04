package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置终端参数应答消息
 */
public class Msg0x8103 {
    public static Map<String, Channel> msg_0x8103 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8103.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8103.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8103.remove(phone);
    }
}
