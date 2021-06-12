package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T8900_0900_terminal_status_answer;
import org.yzh.web.mapper.JsTerminalConfigMapper;
import org.yzh.web.mapper.JsTerminalStatusMapper;
import org.yzh.web.model.entity.JsTerminalConfig;
import org.yzh.web.model.entity.JsTerminalStatus;
import org.yzh.web.service.TerminalConfigService;
import org.yzh.web.service.TerminalStatusService;

@Service
public class TerminalStatusServiceImpl implements TerminalStatusService {

    private static final Logger log = LoggerFactory.getLogger(TerminalStatusServiceImpl.class.getSimpleName());

    @Autowired
    private JsTerminalStatusMapper jsTerminalStatusMapper;

    @Override
    public boolean updateTerminalStatusStatus(String phone, Integer downStatus,T8900_0900_terminal_status_answer message) {
        JsTerminalStatus record = new JsTerminalStatus();
        record.setDownStatus(downStatus);
        record.setPhone(phone);
        int num = 0;
        if(message != null){
            record.setStatus(message.getStatus());
            num = jsTerminalStatusMapper.updateByStatus(record);
        }else{
            num = jsTerminalStatusMapper.updateByStatusOnly(record);
        }
        if(num == 0){
            return false;
        }else{
            return true;
        }
    }




}