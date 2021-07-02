package org.yzh.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bin;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Action;
import org.yzh.protocol.commons.ShapeAction;
import org.yzh.protocol.commons.transform.Attribute;
import org.yzh.protocol.commons.transform.ParameterType;
import org.yzh.protocol.commons.transform.attribute.*;
import org.yzh.protocol.t808.*;
import org.yzh.web.sign.Sign;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
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

    /** 2013版消息头 */
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
//        return message;
//    }

    /** 2019版消息头 */
    public static AbstractMessage H2019(AbstractMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        if (type != null && message.getMessageId() == 0){
            if(type.value()[0].indexOf("_")  > -1){
                String[] value = type.value()[0].split("_");
                header.setMessageId(Integer.parseInt(value[0].substring(2),16));

            }else{
                header.setMessageId(Integer.parseInt(type.value()[0].substring(2),16));
            }
        }else{
            header.setMessageId(message.getMessageId());
        }
        header.setVersionNo(0);
        header.setMobileNo("17299841738");
        header.setSerialNo((int) Short.MAX_VALUE);
        header.setEncryption(0);//加密方式
        header.setVersion(true);
        header.setReserved(0);
//        header.setPackageNo(2);
//        header.setPackageTotal(2);
//        header.setSubpackage(true);
//        log.info("分包：{}",header.isSubpackage());
        message.setHeader(header);

        return message;
    }

    //平台RSA公钥|终端RSA公钥
    public static T0A00_8A00 T0A00_8A00() {
        T0A00_8A00 bean = new T0A00_8A00();
        bean.setE(999);
        bean.setN(new byte[128]);
        return bean;
    }

    //终端通用应答|平台通用应答
    public static T0001 T0001() {
        T0001 bean = new T0001();
        bean.setSerialNo(123);
        bean.setReplyId(456);
        bean.setResultCode(3);
        return bean;
    }

    //终端心跳
    public static T0002 T0002() {
        T8900_0900_photo_command result = new T8900_0900_photo_command();
        result.setPhotoSize(0x04);

        log.info("ps:{}",result.getPhotoSize());
//        log.info("ps2:{}",Integer.valueOf("0x04"));
        log.info("ps3:{}",Integer.valueOf("0x04".replaceAll("^0[x|X]", ""),16));
        T0002 bean = new T0002();
        return bean;
    }

    //终端注册
    public static T0100 T0100() {
        T0100 bean = new T0100();
        bean.setProvinceId(31);
        bean.setCityId(115);
        bean.setVenderId("2");
        bean.setModel("普通桑塔纳");
        bean.setSn("DE158QW");
        bean.setImei("ED323434");
        bean.setPlateColor(1);
        bean.setPlateNo("苏E7G16学");
        return bean;
    }

    //终端注销
    public static T0003 T0003() {
        T0003 bean = new T0003();
        return bean;
    }

    //上报学员登录
    public static T8900_0900_student_login T8900_0900_student_login(){
        T8900_0900_student_login bean = new T8900_0900_student_login();
        bean.setTerminalNo("ERu9zzICPFdiyOlV");
        bean.setStudentNo("E0OzzeesmQnf1lkM");
        bean.setCoachNo("cbUVQoEts6eJXd8p");
        bean.setClassName("21000");
        bean.setClassName("1012010000");
        bean.setClassId(56);
        return bean;

    }

    //上报学员登出
    public static T8900_0900_student_logout T8900_0900_student_logout(){
        T8900_0900_student_logout bean = new T8900_0900_student_logout();
        bean.setTerminalNo("ERu9zzICPFdiyOlV");
        bean.setStudentNo("E0OzzeesmQnf1lkM");
        bean.setDateTime(TIME);
        bean.setTotalKm(4);
        bean.setTotalTime(20);
        bean.setClassId(55);
        return bean;
    }

    //上报学时记录
    public static T8900_0900_time_up T8900_0900_time_up(){
        T8900_0900_time_up bean = new T8900_0900_time_up();
        bean.setTerminalNo("ERu9zzICPFdiyOlV");
        bean.setTimeNo("0000000000000000202101270001");
        bean.setUpType(0x01);
        bean.setStudentNo("E0OzzeesmQnf1lkM");
        bean.setCoachNo("cbUVQoEts6eJXd8p");
        bean.setClassId(1);
        bean.setAddTime("121101");
        bean.setClassNum("1212110000");
        bean.setStatus(0);
        bean.setSpeed(42);
        bean.setKm(2);
        return bean;
    }

    //上报教练员登录
    public static T8900_0900_coach_login T8900_0900_coach_login(){
        T8900_0900_coach_login bean = new T8900_0900_coach_login();
        bean.setTerminalNo("0");
        bean.setCoachIdentity("340881199510272853");
        bean.setCoachNo("457");
        bean.setCoachType("A1");
        return bean;

    }

    //上报教练员登出
    public static T8900_0900_coach_logout T8900_0900_coach_logout(){
        T8900_0900_coach_logout bean = new T8900_0900_coach_logout();
        bean.setTerminalNo("0");
        bean.setCoachNo("457");
        return bean;

    }

    //终端鉴权
    public static T0102 T0102_2013() {
        T0102 bean = new T0102();
        long timeStamp = 1619573078000L;
        bean.setTimeStamp(timeStamp);
        Sign sign = new Sign();
//        sign.signTest("absckedda",timeStamp,,"e2");
        bean.setToken("FF6823FF041853F9FF2014F7F4FF64574029F2F242FFFFF4FFFFFFFFFF52FF65FFF2FF77F508FF97564487F3FFFFFFFF56F6FFFF26FF0342FFFF42FFFF7840156109F0FFFFF7FFF1F8FFF9FFFF70F8FFFFFFFFF19411FFFF01588237FF07F5FFF0FF50FF15FFF1FFFF05F3FFFFFFF3FFF411FF89FF199517FF56FF308248FF65FFF2FFF6FFFF99FF96FF53FFFFFFFFFFFF15FF72F7FFFFFFFFF678F3F554FF30F468FFFFFFFF23F7FFF3F1F91076F09693F399FFFF46F594F6FFFFF5FFF711F1FF13FF49F3F120F014FFFF85FFFFFFFFFFF063F92137209620FFF69330FFFFFF92F1FFF337FFFFF6FFF50827FF56FFFF329305F892FFFF15F681FF99470419F5".getBytes());
        return bean;
    }

    //位置信息汇报
    public static T0200 T0200() {
        T0200 bean = new T0200(1,"17299841738");
        log.info("int:{}",Integer.parseInt("1001100110100001010100011100",2));
        bean.setWarningMark(Integer.parseInt("00001001100110100001010100011100",2));
        bean.setStatus(Integer.parseInt("100011100",2));
        bean.setLatitude(116307629);
        bean.setLongitude(40058359);
        bean.setDriveSpeed(5);
        bean.setStarSpeed(3);
        bean.setDirection(99);
        bean.setDateTime(TIME);
        Map<Integer, Attribute> attributes = new TreeMap();
        attributes.put(Mileage.attributeId, new Mileage(11));
        attributes.put(Oil.attributeId, new Oil(22));
        attributes.put(Speed.attributeId, new Speed(33));
        attributes.put(AlarmEventId.attributeId, new AlarmEventId(44));
        attributes.put(TirePressure.attributeId, new TirePressure((byte) 55, (byte) 55, (byte) 55));
        attributes.put(CarriageTemperature.attributeId, new CarriageTemperature(2));
        attributes.put(OverSpeedAlarm.attributeId, new OverSpeedAlarm((byte) 66, 66));
        attributes.put(InOutAreaAlarm.attributeId, new InOutAreaAlarm((byte) 77, 77, (byte) 77));
        attributes.put(RouteDriveTimeAlarm.attributeId, new RouteDriveTimeAlarm(88, 88, (byte) 88));
        attributes.put(Signal.attributeId, new Signal(99));
        attributes.put(IoState.attributeId, new IoState(10));
        attributes.put(AnalogQuantity.attributeId, new AnalogQuantity(20));
        attributes.put(SignalStrength.attributeId, new SignalStrength(30));
        attributes.put(GnssCount.attributeId, new GnssCount(40));
        bean.setAttributes(attributes);
        log.info("m----s---g:{}",bean.getMessageId());
        return bean;
    }


    //上报照片查询结果
    public static T8900_0900_photo_search_result_up T8900_0900_photo_search_result_up(){
        T8900_0900_photo_search_result_up bean = new T8900_0900_photo_search_result_up();
        bean.setTerminalNo("0");
        bean.setIsUp(1);
        bean.setNeedNum(5);
        bean.setNeedNum(3);
        bean.setPhotoNum1("ph001");
        bean.setPhotoNum2("ph002");
        bean.setPhotoNum3("ph003");
        return bean;
    }

    //照片上传初始化
    public static T8900_0900_photo_up_init T8900_0900_photo_up_init(){
        T8900_0900_photo_up_init bean = new T8900_0900_photo_up_init();
        bean.setTerminalNo("0");
        bean.setChannelNo(1);
        bean.setUpMode(1);
        bean.setPhotoNum("ph001");
        bean.setPhotoSize(5);
        bean.setEventType(1);
        bean.setTotalPacket(1);
        bean.setPhotoDataSize(1080);
        bean.setClassId(1001);
        return bean;
    }

    //上传照片数据包
    public static T8900_0900_photo_up_data T8900_0900_photo_up_data(){
        T8900_0900_photo_up_data bean = new T8900_0900_photo_up_data();
        bean.setPhotoNum("ph001");
        bean.setTerminalNo("0");
//        File file = new File("F:\\timg.jpg");
        File file = new File("F:\\ResponseData.txt");
        byte buf[] = new byte[(int)file.length()];
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(buf);
//            FileOutputStream fs=new FileOutputStream("F:\\timg.jpg");
//            fs.write(buf,0,0);
//            fs.close();
            bean.setPhotoData(buf);
        }catch (IOException ex){
            log.info("IOException:{}",ex);
        }

        return bean;
    }

    //--------------------------------------------------------------



    //补传分包请求
    public static T8003 T8003() {
        T8003 bean = new T8003();
        bean.setSerialNo(4249);
        bean.setItems(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }


    //数据上行透传|数据下行透传
    public static T8900_0900 T8900_0900() {
        T8900_0900 bean = new T8900_0900();
        T8900_0900_content content = new T8900_0900_content();
        content.setMsgId(0x0101);
        content.setMsgAttr(0x0101);
        content.setDataLength(0);
        content.setPacketNo(1);
        content.setTerminalNo("100121313Ab111");

        T8900_0900_coach_up t8900_0900_coach_up = new T8900_0900_coach_up();
        t8900_0900_coach_up.setCoachIdentity("34242519911221811x");
        t8900_0900_coach_up.setCoachNo("J0071sa");
        t8900_0900_coach_up.setCoachType("C1");

        T0200 t0200 = new T0200();
        t0200.setWarningMark(1024 * 2);
        t0200.setStatus(2048 * 2);
        t0200.setLatitude(116307629 * 2);
        t0200.setLongitude(40058359 * 2);
        t0200.setDriveSpeed(5 * 2);
        t0200.setStarSpeed(3 * 2);
        t0200.setDirection(99 * 2);
        t0200.setDateTime(TIME.plusYears(1));

        t8900_0900_coach_up.setT0200(t0200);
        content.setT8900_0900_coach_up(t8900_0900_coach_up);
        bean.setContent(content);
//        bean.setContent(content.getBytes());
//        bean.setContent("1".getBytes());
        return bean;
    }



}