package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsTerminalOperateParamMapper;
import org.yzh.web.model.entity.JsTerminalOperateParam;
import org.yzh.web.service.JsTerminalOperateParamService;

@Service
public class JsTerminalOperateParamServiceImpl implements JsTerminalOperateParamService {

    private static final Logger log = LoggerFactory.getLogger(JsTerminalOperateParamServiceImpl.class.getSimpleName());

    @Autowired
    private JsTerminalOperateParamMapper jsTerminalOperateParamMapper;

    @Override
    public boolean updateTerminalAppConfigStatus(String phone, Integer status) {
        JsTerminalOperateParam jsTerminalOperateParam = new JsTerminalOperateParam();
        jsTerminalOperateParam.setDownStatus(status);
        jsTerminalOperateParam.setPhone(phone);
        int num = jsTerminalOperateParamMapper.updateByStatus(jsTerminalOperateParam);
        if(num == 0){
            return false;
        }else{
            return true;
        }
    }




}