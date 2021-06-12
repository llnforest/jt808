package org.yzh.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bytes;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.transform.Attribute;
import org.yzh.protocol.commons.transform.ParameterType;
import org.yzh.protocol.commons.transform.TerminalParameterUtils;
import org.yzh.protocol.commons.transform.attribute.*;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.StringUtil;
import org.yzh.web.endpoint.JT808Endpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.yzh.protocol.commons.JT808.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Endpoint
public class ServerEndpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    private AtomicInteger serialNo = new AtomicInteger();

    private String mobileNo = "17299841738";

    @Mapping(types = 平台登录应答, desc = "平台登录应答")
    public T81F0 平台登录应答(T01F0 message) {
        T81F0 bean = new T81F0(1,mobileNo);
        bean.setResult(0);
        return bean;
    }


    @Mapping(types = 平台通用应答, desc = "平台通用应答")
    public T0001 平台通用应答(T0001 message) {
//        Header header = message.getHeader();
//        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
//        //TODO
//        result.setSerialNo(header.getSerialNo());
//        result.setReplyId(header.getMessageId());
//        return result;
        return null;
    }



}