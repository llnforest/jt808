package org.yzh.web.reply;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置计时终端应用参数
 */
public class Msg0x8501 {
    public static Map<String, Channel> msg_0x8501 = new HashMap<>();

    public static void setMsg(String phone,Channel channel){
        msg_0x8501.put(phone,channel);
    }

    public static Channel getMsg(String phone){
        return msg_0x8501.get(phone);
    }

    public static void delMsg(String phone){
        msg_0x8501.remove(phone);
    }
}
