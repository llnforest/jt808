package org.yzh.web.protocol;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.Const;
import org.yzh.framework.commons.transform.Bytes;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.transform.ParameterType;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.BeanHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * JT/T 808协议单元测试数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JT808Beans {
    private static final Logger log = LoggerFactory.getLogger(JT808Beans.class.getSimpleName());
    private static final LocalDateTime TIME = LocalDateTime.of(2020, 7, 7, 19, 23, 59);
    private static final String STR16 = "O8gYkVE6kfz8ec6Y";
    private static final Random R = new Random(1);

//    /** 2013版消息头 */
//    public static AbstractMessage H2013(AbstractMessage message) {
//        Header header = new Header();
//        Message type = message.getClass().getAnnotation(Message.class);
//        if (type != null){
//            if(type.value()[0].indexOf("_")  > -1){
//                String[] value = type.value()[0].split("_");
//                header.setMessageId(Integer.parseInt(value[0].substring(2),16));
//
//            }else{
//                header.setMessageId(Integer.parseInt(type.value()[0].substring(2),16));
//            }
//        }
//        header.setMobileNo("122345678901");
//        header.setSerialNo((int) Short.MAX_VALUE);
//        header.setEncryption(0);
//        header.setReserved(0);
//        message.setHeader(header);
//
//        return message;
//    }

    /** 2019版消息头 */
    public static AbstractMessage H2019(AbstractMessage message,String phone,int serialNo) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        if (type != null){
            if(type.value()[0].indexOf("_")  > -1){
                String[] value = type.value()[0].split("_");
                header.setMessageId(Integer.parseInt(value[0].substring(2),16));

            }else{
                header.setMessageId(Integer.parseInt(type.value()[0].substring(2),16));
            }
        }
        header.setVersionNo(1);
        header.setMobileNo(phone);
        header.setSerialNo(serialNo);
        header.setEncryption(0);
        header.setVersion(true);
        header.setReserved(0);
        message.setHeader(header);
        return message;
    }
    //-----------------------平台部分---------------------------


    //-----------------------公共部分---------------------------

    //平台RSA公钥|终端RSA公钥
    public static T0A00_8A00 T0A00_8A00() {
        T0A00_8A00 bean = new T0A00_8A00();
        bean.setE(999);
        bean.setN(new byte[128]);
        return bean;
    }

    //终端心跳
    public static T0002 T0002() {
        T0002 bean = new T0002();
        return bean;
    }

    //-----------------------已完成---------------------------

    //设置终端参数
    public static T8103 T8103(Map<String,Object> map) {
        T8103 bean = new T8103();
        ParameterType[] values = ParameterType.values();
        Map<String,String> paramMap = (Map<String, String>) map.get("data");
        String msgId = "";
        int paramNum = 0;
        for (int i = 0; i < values.length; i++) {
            ParameterType p = values[i];
            log.info("pid：{}",p.id);
            msgId = "0x"+StringUtils.leftPad(Integer.toHexString(p.id),4,"0");
            log.info(msgId);
            if(!paramMap.containsKey(msgId)){
                log.info("少了：{}",msgId);
                continue;
            }
            String[] valueArr =  paramMap.get(msgId).split(",");
            //兼容同一参数多值传递的
            for(int j = 0; j < valueArr.length; j++){
                switch (p.type) {
                    case BYTE:
                    case WORD:
                    case DWORD:
                        bean.addParameter(new BytesParameter(p.id, Integer.valueOf(valueArr[j])));
                        log.info("Bean:{}",bean);
                        break;
                    default:
                        bean.addParameter(new BytesParameter(p.id, valueArr[j]));
                        break;
                }
                paramNum ++;
            }
        }
        bean.setTotal(paramNum);
        bean.setPacketNum(paramNum);

        return bean;
    }

    //查询指定终端参数
    public static T8106 T8106(Map<String,Object> map) {
        T8106 bean = new T8106();
        List<String> hexArr = (List<String>) map.get("data");
//        bean.setTotal(hexArr.size());
        log.info("hexArr:{}",hexArr.size());
//        String[] hexArr = new String[]{"0x0001","0x0085"};
        byte[] byteArr = Bytes.hexListToByte(hexArr);
//        int[] idArr = Bytes.byteToIntArr(byteArr);

        bean.setId(byteArr);
        log.info("hexArr:{}",bean.getTotal());
        return bean;
    }


    //查询终端参数
    public static T8104 T8104(Map<String,Object> map) {
        T8104 bean = new T8104();
        return bean;
    }

    //查询终端参数
    public static T8104 T8000() {
        T8104 bean = new T8104();
        return bean;
    }

    //命令上报学时记录
    public static T8900_0900_time_up_command t8900_0900_time_up_command(Map<String,Object> map,Session session) {
        T8900_0900_time_up_command bean = new T8900_0900_time_up_command();

        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));
        bean.setDataLength(15);

        Map<String,String> dataMap = (Map<String, String>) map.get("data");
        bean.setSearchType(Integer.parseInt(dataMap.get("searchType")));//1按时间上传 2按条数上传
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(dataMap.get("startDate"),df);
        LocalDateTime endTime = LocalDateTime.parse(dataMap.get("endDate"),df);
        bean.setStartTime(startTime);
        bean.setEndTime(endTime);
        bean.setSearchNum(Integer.parseInt(dataMap.get("searchNumber")));//条数
        return bean;
    }

    //设置禁训状态
    public static T8900_0900_terminal_status t8900_0900_terminal_status(Map<String,Object> map,Session session) {
        T8900_0900_terminal_status bean = new T8900_0900_terminal_status();
        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));
        Map<String,String> dataMap = (Map<String, String>) map.get("data");

        bean.setStatus(Integer.parseInt(dataMap.get("status")));//1：可用，默认值；2：禁用
        bean.setMsgContent(dataMap.get("msgContent"));
        return bean;
    }


    //设置计时终端应用参数
    public static T8900_0900_terminal_set t8900_0900_terminal_set(Map<String,Object> map,Session session) {
        T8900_0900_terminal_set bean = new T8900_0900_terminal_set();
        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));
        bean.setDataLength(15);
        Map<String,String> dataMap = (Map<String, String>) map.get("data");

        bean.setParamNo(Integer.valueOf(dataMap.get("paramNo")));
        bean.setPhotoTime(Integer.valueOf(dataMap.get("photoTime")));
        bean.setUpSet(Integer.valueOf(dataMap.get("upSet")));
        bean.setIsReadAdd(Integer.valueOf(dataMap.get("isReadAdd")));
        bean.setClassDelayTime(Integer.valueOf(dataMap.get("classDelayTime")));
        bean.setUpSet(Integer.valueOf(dataMap.get("upSet")));
        bean.setCoachDelayTime(Integer.valueOf(dataMap.get("coachDelayTime")));
        bean.setVerifyTime(Integer.valueOf(dataMap.get("verifyTime")));
        bean.setIsCoachAcross(Integer.valueOf(dataMap.get("isCoachAcross")));
        bean.setIsStudentAcross(Integer.valueOf(dataMap.get("isStudentAcross")));
        bean.setResponseTime(Integer.valueOf(dataMap.get("responseTime")));
        return bean;
    }

    //查询计时终端应用参数
    public static T8900_0900_terminal_param_search t8900_0900_terminal_param_search(Map<String,Object> map,Session session) {
        T8900_0900_terminal_param_search bean = new T8900_0900_terminal_param_search();
        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));
        bean.setDataLength(0);
        return bean;
    }

    //终端控制
    public static T8105 T8105(Map<String,Object> map) {
        T8105 bean = new T8105();
        Map<String,String> dataMap = (Map<String, String>) map.get("data");
        bean.setCommand(Integer.valueOf(dataMap.get("command")));
        bean.setParameter(dataMap.get("parameter"));
        return bean;
    }

    //位置信息查询
    public static T8201 T8201(Map<String,Object> map) {
        T8201 bean = new T8201();
        return bean;
    }

    //临时位置跟踪控制
    public static T8202 T8202(Map<String,Object> map) {
        T8202 bean = new T8202();
        Map<String,String> dataMap = (Map<String, String>) map.get("data");
        bean.setInterval(Integer.valueOf(dataMap.get("interval")));
        bean.setValidityPeriod(Integer.valueOf(dataMap.get("validityPeriod")));
        return bean;
    }

    //立即拍照
    public static T8900_0900_photo_command t8900_0900_photo_command(Map<String,Object> map,Session session) {
        T8900_0900_photo_command bean = new T8900_0900_photo_command();
        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));

        bean.setDataLength(3);
        Map<String,String> dataMap = (Map<String, String>) map.get("data");

        bean.setUpMode(Integer.valueOf(dataMap.get("uploadMode")));//上传模式
        bean.setChannelNo(Integer.valueOf(dataMap.get("cameraNumber")));//通道
        bean.setPhotoSize(Integer.valueOf(dataMap.get("photoSize").replaceAll("^0[x|X]", ""),16));//图片尺寸
        return bean;
    }

    //查询照片
    public static T8900_0900_photo_search_command t8900_0900_photo_search_command(Map<String,Object> map,Session session) {
        T8900_0900_photo_search_command bean = new T8900_0900_photo_search_command();
        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));

        bean.setDataLength(13);
        Map<String,String> dataMap = (Map<String, String>) map.get("data");

        bean.setSearchType(Integer.valueOf(dataMap.get("searchMode")));//1：按时间查询
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(dataMap.get("startDate"),df);
        LocalDateTime endTime = LocalDateTime.parse(dataMap.get("endDate"),df);
        bean.setStartTime(startTime);
        bean.setEndTime(endTime);
        return bean;
    }

    //上传指定照片
    public static T8900_0900_photo_up_only t8900_0900_photo_up_only(Map<String,Object> map,Session session) {
        T8900_0900_photo_up_only bean = new T8900_0900_photo_up_only();
        bean.setPacketNo(session.nextPacketNo());
        bean.setTerminalNo((String)map.get("devNum"));

        bean.setDataLength(1);
        Map<String,String> dataMap = (Map<String, String>) map.get("data");


        bean.setPhotoNum(dataMap.get("photoNum"));//照片编号
        return bean;
    }
    //-----------------------待完成---------------------------







}