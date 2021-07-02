package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsPlatMapper;
import org.yzh.web.model.entity.JsPlat;
import org.yzh.web.service.JsPlatService;

@Service
public class JsPlatServiceImpl implements JsPlatService {

    private static final Logger log = LoggerFactory.getLogger(JsPlatServiceImpl.class.getSimpleName());

    @Autowired
    private JsPlatMapper jsPlatMapper;


    @Override
    public JsPlat findById(int id) {
        JsPlat jsPlat = jsPlatMapper.selectByPrimaryKey(id);
        return jsPlat;
    }

    @Override
    public int update(JsPlat record) {
        int num = jsPlatMapper.updateByPrimaryKeySelective(record);
        return  num;
    }
}