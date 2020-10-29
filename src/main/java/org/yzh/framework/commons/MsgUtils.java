package org.yzh.framework.commons;

import io.netty.channel.ChannelHandlerContext;
import io.swagger.models.auth.In;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MsgUtils {
    private static final Logger log = LoggerFactory.getLogger(MsgUtils.class);
    private static int retransTimes = 3;//总共重传3次
    private static int retransTime = 10;//重传秒数

    public static class SendMsg{
        public int times;
        public long time;
        public AbstractMessage data;

    }

    private static Map<Integer,Map<String, SendMsg>> msgMap = new HashMap<>();

    public static void delMsg(String key,Session session){
        Map<String,SendMsg> channelMap = msgMap.get(session.getId());
        channelMap.remove(key);
    }

    public static void addMsg(String key, AbstractMessage msg, Session session){
        SendMsg sendMsg = new SendMsg();
        log.info("初始：{}",sendMsg.times);
        sendMsg.times = 1;
        sendMsg.data = msg;
        sendMsg.time = System.currentTimeMillis() + retransTime * 1000;
        String k = session.getId()+"_"+key;
        Map<String, SendMsg> channelMap = new HashMap<>();
        channelMap.put(key,sendMsg);
        msgMap.put(session.getId(),channelMap);
    }

    public static void addMsg(String key,SendMsg sendMsg,Map<String,SendMsg> channelMap){
        sendMsg.times = sendMsg.times + 1;
        sendMsg.time = System.currentTimeMillis() + retransTime * sendMsg.times * 1000;
        channelMap.put(key,sendMsg);
    }


    public static void checkMsg(ChannelHandlerContext ctx){
        int channelId = ctx.channel().id().hashCode();
        ctx.executor().scheduleAtFixedRate(() -> {
            Map<String,SendMsg> channelMap = msgMap.get(channelId);
//            log.info("3s定时器,map:{}",channelMap);
            if (channelMap != null && channelMap.size() > 0) {
                channelMap.forEach((k, v) -> {
                    if (System.currentTimeMillis() - v.time >= 0) {
                        log.info("定时第" + v.times + "次发送");
                        if (v.times <= retransTimes) {
                            ctx.writeAndFlush(v.data);
                            // 更新未发送三次的。
                            addMsg(k, v,channelMap);
                        } else {
                            log.info("超过" + retransTimes + "次发送未收到回复，该条信息不再发送");
                            channelMap.remove(k);
                        }
                    }

                });
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

}
