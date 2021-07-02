package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.mapper.JsTrainRecordMapper;
import org.yzh.web.service.JsTrainRecordService;

@Service
public class JsTrainRecordServiceImpl implements JsTrainRecordService {

    private static final Logger log = LoggerFactory.getLogger(JsTrainRecordServiceImpl.class.getSimpleName());

    @Autowired
    private JsTrainRecordMapper jsTrainRecordMapper;



}