package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsPlatLoginMapper;
import org.yzh.web.model.entity.JsPlatLogin;
import org.yzh.web.service.JsPlatLoginService;

@Service
public class JsPlatLoginImpl implements JsPlatLoginService {

    private static final Logger log = LoggerFactory.getLogger(JsPlatLoginImpl.class.getSimpleName());

    @Autowired
    private JsPlatLoginMapper jsPlatLoginMapper;


    @Override
    public JsPlatLogin findById(int id) {
        JsPlatLogin jsPlatLogin = jsPlatLoginMapper.selectByPrimaryKey(id);
        return jsPlatLogin;
    }

    @Override
    public int insert(JsPlatLogin record) {
        int id = jsPlatLoginMapper.insert(record);
        return id;
    }
}