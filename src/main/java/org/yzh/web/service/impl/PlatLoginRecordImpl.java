package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsPlatInfoMapper;
import org.yzh.web.mapper.JsPlatLoginRecordMapper;
import org.yzh.web.model.entity.JsPlatInfo;
import org.yzh.web.model.entity.JsPlatLoginRecord;
import org.yzh.web.service.PlatInfoService;
import org.yzh.web.service.PlatLoginRecordService;

@Service
public class PlatLoginRecordImpl implements PlatLoginRecordService {

    private static final Logger log = LoggerFactory.getLogger(PlatLoginRecordImpl.class.getSimpleName());

    @Autowired
    private JsPlatLoginRecordMapper jsPlatLoginRecordMapper;


    @Override
    public JsPlatLoginRecord findById(int id) {
        JsPlatLoginRecord jsPlatLoginRecord = jsPlatLoginRecordMapper.selectByPrimaryKey(id);
        return jsPlatLoginRecord;
    }

    @Override
    public int insert(JsPlatLoginRecord record) {
        int id = jsPlatLoginRecordMapper.insert(record);
        return id;
    }
}