package org.yzh.web.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.MessageManager;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.model.entity.JsPlat;
import org.yzh.web.model.entity.JsPlatLogin;
import org.yzh.web.service.*;

import java.util.*;

import static org.yzh.protocol.commons.JT808.*;

@Endpoint
@Component
public class ClientEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ClientEndpoint.class.getSimpleName());

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private JsDeviceLocationService jsDeviceLocationService;

    @Autowired
    private JsDeviceService jsDeviceService;


    @Mapping(types = 平台登录应答, desc = "平台登录应答")
    public void 平台登录应答(T81F0 message) {
        //TODO
        JsPlatLogin record = new JsPlatLogin();
        record.setErrorCode(message.getResult());
        record.setPlatId(1);
        record.setLoginTime(new Date());
        JsPlatLoginService service = BeanHelper.getBean(JsPlatLoginService.class);
        service.insert(record);
        JsPlatService jsPlatService = BeanHelper.getBean(JsPlatService.class);
        JsPlat jsPlat = new JsPlat();
        jsPlat.setId(1);
        if(message.getResult() == 0){
            jsPlat.setTcpStatus(1);
        }else{
            jsPlat.setTcpStatus(0);
        }
        jsPlatService.update(jsPlat);

    }

    @Mapping(types = 平台通用应答, desc = "平台通用应答")
    public void 平台通用应答(T0001 message) {
        log.info("msgID:{},serNo:{},result:{}",message.getReplyId(),message.getSerialNo(),message.getResultCode());
        //TODO
        String replayId = StringUtils.leftPad(Integer.toHexString(message.getReplyId()),4,"0");
        switch(replayId){
            case "01f1":
                //平台登出成功
                if(message.getResultCode() == 0){
                    JsPlatService jsPlatService = BeanHelper.getBean(JsPlatService.class);
                    JsPlat record = new JsPlat();
                    record.setId(1);
                    record.setTcpStatus(2);
                    jsPlatService.update(record);
                }
                break;
            default:
                break;
        }

    }

}