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
        jsClassrecordUp.setUpType(data.getUpType());
        jsClassrecordUp.setMaxspeed(String.valueOf(data.getSpeed()));
        jsClassrecordUp.setMileage(String.valueOf(data.getKm()));

        jsClassrecordUp.setSubCode(data.getClassNum());

        jsClassrecordUp.setClassCode(Integer.valueOf(data.getClassNum().substring(0,1)));
        jsClassrecordUp.setTrainCode(data.getClassNum().substring(1,3));
        jsClassrecordUp.setPartCode(Integer.valueOf(data.getClassNum().substring(3,4)));
        jsClassrecordUp.setProjectCode(data.getClassNum().substring(4,6));
        jsClassrecordUp.setClassId(String.valueOf(data.getClassId()));
        jsClassrecordUp.setStatus(data.getStatus());
        jsClassrecordUpMapper.insertSelective(jsClassrecordUp);
        return jsClassrecordUp;
    }
}