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
        if (type != null)
            header.setMessageId(type.value()[0]);
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
        if (type != null)
            header.setMessageId(type.value()[0]);
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

}