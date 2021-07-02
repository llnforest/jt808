package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsTerminalParamMapper;
import org.yzh.web.model.entity.JsTerminalParam;
import org.yzh.web.service.JsTerminalParamService;

@Service
public class JsTerminalParamServiceImpl implements JsTerminalParamService {

    private static final Logger log = LoggerFactory.getLogger(JsTerminalParamServiceImpl.class.getSimpleName());

    @Autowired
    private JsTerminalParamMapper jsTerminalParamMapper;

    @Override
    public boolean updateTerminalConfigStatus(String phone, Integer status) {
        JsTerminalParam jsTerminalParam = new JsTerminalParam();
        jsTerminalParam.setDownStatus(status);
        jsTerminalParam.setPhone(phone);
        int num = jsTerminalParamMapper.updateByStatus(jsTerminalParam);
        if(num == 0){
            return false;
        }else{
            return true;
        }
    }




}