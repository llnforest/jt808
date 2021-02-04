package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置终端禁训状态应答消息
 */
public class Msg0x8502 {
    public static Map<String, Channel> msg_0x8502 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8502.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8502.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8502.remove(phone);
    }
}
