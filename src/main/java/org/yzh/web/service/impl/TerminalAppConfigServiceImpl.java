package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsTerminalAppConfigMapper;
import org.yzh.web.mapper.JsTerminalConfigMapper;
import org.yzh.web.model.entity.JsTerminalAppConfig;
import org.yzh.web.model.entity.JsTerminalConfig;
import org.yzh.web.service.TerminalAppConfigService;
import org.yzh.web.service.TerminalConfigService;

@Service
public class TerminalAppConfigServiceImpl implements TerminalAppConfigService {

    private static final Logger log = LoggerFactory.getLogger(TerminalAppConfigServiceImpl.class.getSimpleName());

    @Autowired
    private JsTerminalAppConfigMapper jsTerminalAppConfigMapper;

    @Override
    public boolean updateTerminalAppConfigStatus(String phone, Integer status) {
        JsTerminalAppConfig jsTerminalAppConfig = new JsTerminalAppConfig();
        jsTerminalAppConfig.setDownStatus(status);
        jsTerminalAppConfig.setPhone(phone);
        int num = jsTerminalAppConfigMapper.updateByStatus(jsTerminalAppConfig);
        if(num == 0){
            return false;
        }else{
            return true;
        }
    }




}