package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsClassrecordMapper;
import org.yzh.web.mapper.JsTerminalConfigMapper;
import org.yzh.web.model.entity.JsTerminalConfig;
import org.yzh.web.service.ClassRecordService;
import org.yzh.web.service.TerminalConfigService;

@Service
public class TerminalConfigServiceImpl implements TerminalConfigService {

    private static final Logger log = LoggerFactory.getLogger(TerminalConfigServiceImpl.class.getSimpleName());

    @Autowired
    private JsTerminalConfigMapper jsTerminalConfigMapper;

    @Override
    public boolean updateTerminalConfigStatus(String phone, Integer status) {
        JsTerminalConfig jsTerminalConfig = new JsTerminalConfig();
        jsTerminalConfig.setDownStatus(status);
        jsTerminalConfig.setPhone(phone);
        int num = jsTerminalConfigMapper.updateByStatus(jsTerminalConfig);
        if(num == 0){
            return false;
        }else{
            return true;
        }
    }




}