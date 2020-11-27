package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T8900_0900_time_up;
import org.yzh.web.mapper.JsClassrecordUpMapper;
import org.yzh.web.model.entity.JsClassrecordUp;
import org.yzh.web.service.ClassRecordService;
import org.yzh.web.service.ClassRecordUpService;

@Service
public class ClassRecordUpServiceImpl implements ClassRecordUpService {

    private static final Logger log = LoggerFactory.getLogger(ClassRecordUpServiceImpl.class.getSimpleName());

    @Autowired
    private JsClassrecordUpMapper jsClassrecordUpMapper;


    @Override
    public JsClassrecordUp addRecord(T8900_0900_time_up data) {
        JsClassrecordUp jsClassrecordUp = new JsClassrecordUp();
        jsClassrecordUp.setCoachnum(data.getCoachNo());
        jsClassrecordUp.setRnum(data.getTimeNo());
        jsClassrecordUp.setStunum(data.getStudentNo());
        jsClassrecordUp.setUptype(data.getUpType());
        jsClassrecordUp.setMaxspeed(String.valueOf(data.getSpeed()));
        jsClassrecordUp.setMileage(String.valueOf(data.getKm()));
        jsClassrecordUp.setSubjcode(data.getClassNum());
        jsClassrecordUp.setClassid(data.getClassId());
        jsClassrecordUp.setStatus(data.getStatus());
        jsClassrecordUpMapper.insertSelective(jsClassrecordUp);
        return jsClassrecordUp;
    }
}