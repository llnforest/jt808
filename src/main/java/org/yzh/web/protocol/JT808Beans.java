package org.yzh.web.protocol;

import org.yzh.framework.commons.transform.Bin;
import org.yzh.framework.orm.annotation.Message;
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

import java.time.LocalDateTime;
import java.util.*;

/**
 * JT/T 808协议单元测试数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JT808Beans {

    private static final LocalDateTime TIME = LocalDateTime.of(2020, 7, 7, 19, 23, 59);
    private static final String STR16 = "O8gYkVE6kfz8ec6Y";
    private static final Random R = new Random(1);

    /** 2013版消息头 */
    public static AbstractMessage H2013(AbstractMessage message) {
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
        header.setMobileNo("122345678901");
        header.setSerialNo((int) Short.MAX_VALUE);
        header.setEncryption(0);
        header.setReserved(false);
        message.setHeader(header);

        return message;
    }

    /** 2019版消息头 */
    public static AbstractMessage H2019(AbstractMessage message) {
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
        header.setMobileNo("17299841738");
        header.setSerialNo(65535);
        header.setEncryption(0);
        header.setVersion(true);
        header.setReserved(false);
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

    //平台登录
    public static T0090 T0090() {
        T0090 bean = new T0090();
        bean.setPlatNum("abc123");
        bean.setPlatSecret("abcdefg");
        bean.setPlatCode(3121);
        return bean;
    }

    //平台登出
    public static T0091 T0091() {
        T0091 bean = new T0091();
        bean.setPlatNum("abc123");
        bean.setPlatSecret("abcdefg");
        return bean;
    }

    //终端心跳
    public static T0003 T0003() {
        T0003 bean = new T0003();
        return bean;
    }

    //命令上报学时记录
    public static T8900_0900_time_up_command t8900_0900_time_up_command() {
        T8900_0900_time_up_command bean = new T8900_0900_time_up_command();
        bean.setSearchType(1);//1按时间上传 2按条数上传
        bean.setStartTime(TIME);
        bean.setEndTime(TIME);
        bean.setSearchNum(5);//条数
        return bean;
    }

    //立即拍照
    public static T8900_0900_photo_command t8900_0900_photo_command() {
        T8900_0900_photo_command bean = new T8900_0900_photo_command();
        bean.setUpMode(1);//上传模式
        bean.setChannelNo(0);//通道
        bean.setPhotoSize(0x01);//图片尺寸
        return bean;
    }

    //查询照片
    public static T8900_0900_photo_search_command t8900_0900_photo_search_command() {
        T8900_0900_photo_search_command bean = new T8900_0900_photo_search_command();
        bean.setSearchType(1);//1：按时间查询
        bean.setStartTime(TIME);
        bean.setEndTime(TIME);
        return bean;
    }

    //上传指定照片
    public static T8900_0900_photo_up_only t8900_0900_photo_up_only() {
        T8900_0900_photo_up_only bean = new T8900_0900_photo_up_only();
        bean.setPhotoNum("11221212");//1：按时间查询
        return bean;
    }

    //设置计时终端应用参数
    public static T8900_0900_terminal_set t8900_0900_terminal_set() {
        T8900_0900_terminal_set bean = new T8900_0900_terminal_set();
        bean.setParamNo(1);
        bean.setPhotoTime(15);
        bean.setUpSet(1);
        bean.setIsReadAdd(0);
        bean.setClassDelayTime(1);
        bean.setUpSet(3600);
        bean.setCoachDelayTime(150);
        bean.setVerifyTime(30);
        bean.setIsCoachAcross(1);
        bean.setIsStudentAcross(1);
        bean.setResponseTime(3);
        return bean;
    }

    //设置禁训状态
    public static T8900_0900_terminal_status t8900_0900_terminal_status() {
        T8900_0900_terminal_status bean = new T8900_0900_terminal_status();
        bean.setStatus(1);//1：可用，默认值；2：禁用
        bean.setMsgLength(0);
        return bean;
    }

    //查询计时终端应用参数
    public static T8900_0900_terminal_param_search t8900_0900_terminal_param_search() {
        T8900_0900_terminal_param_search bean = new T8900_0900_terminal_param_search();
        return bean;
    }

    //设置终端参数
    public static T8103 T8103() {
        T8103 bean = new T8103();
        bean.setPacketNum(5);
        ParameterType[] values = ParameterType.values();
        for (int i = 0; i < 38; i++) {
            ParameterType p = values[i];
            switch (p.type) {
                case BYTE:
                case WORD:
                case DWORD:
                    bean.addParameter(new BytesParameter(p.id, R.nextInt()));
                default:
                    bean.addParameter(new BytesParameter(p.id, STR16));
            }
        }
        return bean;
    }

    //查询终端参数
    public static T8104 T8104() {
        T8104 bean = new T8104();
        return bean;
    }



    //查询指定终端参数
    public static T8106 T8106() {
        T8106 bean = new T8106();
        bean.setId(new byte[]{1, 3, 5, 7, 9, 127});
        return bean;
    }


    //终端控制
    public static T8105 T8105() {
        T8105 bean = new T8105();
        bean.setCommand(123);
        bean.setParameter("as;123;zxc;123;");
        return bean;
    }

    //临时位置跟踪控制
    public static T8202 T8202() {
        T8202 bean = new T8202();
        bean.setInterval(5);
        bean.setValidityPeriod(600);
        return bean;
    }
}