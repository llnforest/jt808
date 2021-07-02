package org.yzh.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t808.*;
import org.yzh.web.endpoint.JT808Endpoint;

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

    @Mapping(types = 平台登录请求, desc = "平台登录应答")
    public T81F0 平台登录应答(T01F0 message) {
        log.info("1212");
        T81F0 bean = new T81F0(1,mobileNo);
        bean.setResult(0);
        return bean;
    }

    @Mapping(types = 平台登出请求, desc = "平台登录应答")
    public T0001 平台登出应答(T01F1 message) {
        T0001 result = new T0001(serialNo.addAndGet(1),mobileNo);
        result.setSerialNo(message.getHeader().getSerialNo());
        result.setReplyId(message.getHeader().getMessageId());
        result.setResultCode(0);
        return result;
    }

    @Mapping(types = {平台通用应答,终端注册}, desc = "平台通用应答")
    public T0001 平台通用应答(T0001 message, Session session) {
        Header header = message.getHeader();
        T0001 result = new T0001(serialNo.addAndGet(1),mobileNo);
//        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(0);
        return result;
    }



}