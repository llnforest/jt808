package org.yzh.web.endpoint;

import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t1078.T1205;
import org.yzh.protocol.t808.T0001;

import static org.yzh.protocol.commons.JT1078.终端上传音视频资源列表;

@Endpoint
@Component
public class JT1078Endpoint {

    @Mapping(types = 终端上传音视频资源列表, desc = "终端上传音视频资源列表")
    public T0001 终端上传音视频资源列表(T1205 message, Session session) {
        Header header = message.getHeader();

        T0001 result = new T0001(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(message.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }
}