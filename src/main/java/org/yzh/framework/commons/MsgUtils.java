package org.yzh.framework.commons;

import org.eclipse.jetty.util.StringUtil;
import org.yzh.framework.netty.TCPServerHandler;
import org.yzh.framework.orm.model.AbstractMessage;

import java.util.HashMap;
import java.util.Map;

public class MsgUtils {

    class SendMsg{
        public int serialNo;
        public int times;
        public long time;
        public AbstractMessage data;

    }

    private static Map<String, MsgUtils.SendMsg> msgMap = new HashMap<>();

    public static void delMsg(String key){
        msgMap.remove(key);
    }

    public static void addMsg(String key,MsgUtils.SendMsg msg){
        if(msg.times == 0) {
            msg.time = System.currentTimeMillis();
        }
        msgMap.put(key,msg);
    }


}
