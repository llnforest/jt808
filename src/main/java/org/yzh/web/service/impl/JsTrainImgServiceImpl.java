package org.yzh.web.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T8900_0900_photo_up_init;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.mapper.JsTrainImgMapper;
import org.yzh.web.mapper.JsTrainRecordMapper;
import org.yzh.web.model.entity.JsTrainImg;
import org.yzh.web.model.entity.JsTrainRecord;
import org.yzh.web.service.JsTrainImgService;
import org.yzh.web.service.JsTrainLocationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JsTrainImgServiceImpl implements JsTrainImgService {

    private static final Logger log = LoggerFactory.getLogger(JsTrainImgServiceImpl.class.getSimpleName());

    @Autowired
    private JsTrainImgMapper jsTrainImgMapper;

    @Autowired
    private JsTrainRecordMapper jsTrainRecordMapper;

    @Override
    public void insert(T8900_0900_photo_up_init request) {
        JsTrainImg jsTrainImg = new JsTrainImg();
        jsTrainImg.setClassId(String.valueOf(request.getClassId()));
        jsTrainImg.setPhotonum(request.getPhotoNum());
        jsTrainImg.setDevnum(request.getTerminalNo());

        JsTrainRecord jsTrainRecord = new JsTrainRecord();
        jsTrainRecord.setClassId(jsTrainImg.getClassId());
        jsTrainRecord.setDevnum(request.getTerminalNo());
        List<JsTrainRecord> list = jsTrainRecordMapper.select(jsTrainRecord);
        if(CollectionUtils.isEmpty(list)) return;
        JsTrainRecord trainRecord = list.get(0);
        jsTrainImg.setInscode(trainRecord.getInscode());
        jsTrainImg.setCoachnum(trainRecord.getCoachnum());
        jsTrainImg.setStunum(trainRecord.getStunum());
        jsTrainImg.setSubjcode(trainRecord.getSubjcode());
        jsTrainImg.setRecnum(trainRecord.getRecnum());
        jsTrainImg.setUpmode(request.getUpMode());
        jsTrainImg.setUptype(request.getEventType());
        jsTrainImg.setPtime(LocalDateTime.now());
        jsTrainImg.setCreateTime(LocalDateTime.now());
        int imgId = jsTrainImgMapper.insertSelective(jsTrainImg);
        //加入培训过程位置
        JsTrainLocationService jsTrainLocationService = BeanHelper.getBean(JsTrainLocationServiceImpl.class);
        jsTrainLocationService.insert(request.getT0200(),request.getHeader().getMobileNo(),1,imgId);
    }
}