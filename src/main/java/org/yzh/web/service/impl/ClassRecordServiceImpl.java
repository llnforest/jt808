package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsClassrecordMapper;
import org.yzh.web.service.ClassRecordService;

@Service
public class ClassRecordServiceImpl implements ClassRecordService {

    private static final Logger log = LoggerFactory.getLogger(ClassRecordServiceImpl.class.getSimpleName());

    @Autowired
    private JsClassrecordMapper jsClassrecordMapper;



}