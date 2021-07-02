package org.yzh.framework.commons;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.yzh.framework.netty.TCPServer;
import org.yzh.framework.netty.client.TCPClient;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.StringUtil;

import java.util.*;

public class TcpServerUtils {
    private static TCPClient client;
    //待转发的消息
    public static Map<String, List<byte[]>> replayMsgMap = new HashMap<>();
    //分包消息
    public static Map<String, List<byte[]>> packetMsgMap = new HashMap<>();

    public static void removeMsgByPhone(String phone){
        if(StringUtils.isEmpty(phone)) return ;
        for(String key:replayMsgMap.keySet()){
            if(key.startsWith(phone)) replayMsgMap.remove(key);
        }

        for(String key:packetMsgMap.keySet()){
            if(key.startsWith(phone)) packetMsgMap.remove(key);
        }
    }

    //添加分包消息
    public static void setPacketMsgMap(AbstractHeader header,byte[] bytes){
        String key = getPMsgKey(header);
        List<byte[]> list = new ArrayList<>();
        list.add(bytes);
        packetMsgMap.put(key,list);
    }

    //分包消息转待转发消息
    public static void transPacketMsgMap(AbstractMessage msg){
        if(isNeedReplay(msg)){
            AbstractHeader header = msg.getHeader();
            String pKey = getPMsgKey(header);
            replayMsgMap.put(getMsgKey(msg),packetMsgMap.get(pKey));
            packetMsgMap.remove(pKey);
        }
    }


    //添加待转发消息
    public static void setReplayMsgMap(AbstractMessage msg,byte[] bytes){
        if(isNeedReplay(msg)){
            String key = getMsgKey(msg);
            System.out.println(key);
            List<byte[]> list = new ArrayList<>();
            list.add(bytes);
            replayMsgMap.put(key,list);
        }
    }

    //转发消息
    private static void sendReplayMsgMap(AbstractMessage msg){
        String key = getMsgKey(msg);
        System.out.println(key);
        if(replayMsgMap.containsKey(key)){
            List<byte[]> list = replayMsgMap.get(key);
            for(byte[] bytes:list){
                System.out.println("---------send-----------");
                client.writeObject(bytes);
            }
        }
    }

    //删除转发消息
    public static void rmMsgKey(String key){
        replayMsgMap.remove(key);
    }

    //获取转发消息key
    private static String getMsgKey(AbstractMessage msg){
        AbstractHeader header = msg.getHeader();
        return header.getMobileNo()+"_"+msg.getMarkId()+"_"+header.getSerialNo();
    }

    private static String getPMsgKey(AbstractHeader header){
        return header.getMobileNo()+"_"+header.getMessageId()+"_"+header.getSerialNo();
    }

    private static boolean isNeedReplay(AbstractMessage msg){
        if(contains(new String[]{JT808.终端注册,JT808.终端注销,JT808.终端鉴权,JT808.位置信息汇报,JT808.上报教练员登录,JT808.上报教练员登出,JT808.上报学员登录,JT808.上报学员登出,JT808.上报学时记录,JT808.照片上传初始化,JT808.照片上传数据包},msg.getMarkId()) && Const.isMsgReplay){
            return true;
        }else{
            return false;
        }
    }

    //向上级转发消息
    public static void replayMsg(AbstractMessage request,AbstractMessage response){
        System.out.println("-------------------ok------------");
        if(isNeedReplay(request)){
            switch (request.getMarkId()){
                case JT808.终端注册:
                        sendReplayMsgMap(request);
                    if(((T8100) response).getResultCode() == 0){
                    }
                    break;
                case JT808.终端注销:
                    if(((T0001) response).getResultCode() == 0){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.终端鉴权:
                    if(((T0001) response).getResultCode() == 0){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.位置信息汇报:
                    if(((T0001) response).getResultCode() == 0){
                        sendReplayMsgMap(request);
                    }
                    break;

                case JT808.上报教练员登录:
                    if(((T8900_0900_coach_login_answer) response).getLoginResult() == 1){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.上报教练员登出:
                    if(((T8900_0900_coach_logout_answer) response).getLoginResult() == 1){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.上报学员登录:
                    if(((T8900_0900_student_login_answer) response).getLoginResult() == 1){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.上报学员登出:
                    if(((T8900_0900_student_logout_answer) response).getLoginResult() == 1){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.上报学时记录:
                    if(((T0001) response).getResultCode() == 0){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.照片上传初始化:
                    if(((T8900_0900_photo_up_init_answer) response).getResult() == 0){
                        sendReplayMsgMap(request);
                    }
                    break;
                case JT808.照片上传数据包:
                    if(((T0001) response).getResultCode() == 0){
                        sendReplayMsgMap(request);
                    }
                    break;
                default:
                    break;
            }
            rmMsgKey(getMsgKey(request));//删除消息
        }
    }

    public static boolean contains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);
        // 利用list的包含方法,进行判断
        if(tempList.contains(source))
        {
            return true;
        } else {
            return false;
        }
    }

    //设置客户端
    public static void setClient(TCPClient client1){
        client = client1;
    }

    //获取客户端
    public static  TCPClient getClient(){
        return client;
    }
}
