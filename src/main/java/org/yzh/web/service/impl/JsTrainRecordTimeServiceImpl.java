package org.yzh.web.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T8900_0900_time_up;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.mapper.JsTrainRecordTimeMapper;
import org.yzh.web.mapper.SysMenuMapper;
import org.yzh.web.model.entity.JsTrainRecordTime;
import org.yzh.web.model.entity.SysMenu;
import org.yzh.web.service.JsTrainLocationService;
import org.yzh.web.service.JsTrainRecordTimeService;

import java.util.List;

@Service
public class JsTrainRecordTimeServiceImpl implements JsTrainRecordTimeService {

    private static final Logger log = LoggerFactory.getLogger(JsTrainRecordTimeServiceImpl.class.getSimpleName());

    @Autowired
    private JsTrainRecordTimeMapper jsTrainRecordTimeMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public JsTrainRecordTime addRecord(T8900_0900_time_up data) {
        JsTrainRecordTime jsTrainRecordTime = new JsTrainRecordTime();
        jsTrainRecordTime.setCoachnum(data.getCoachNo());
        jsTrainRecordTime.setRnum(data.getTimeNo());
        jsTrainRecordTime.setStunum(data.getStudentNo());
        jsTrainRecordTime.setUpType(data.getUpType());
        jsTrainRecordTime.setMaxspeed(String.valueOf(data.getSpeed()));
        jsTrainRecordTime.setMileage(String.valueOf(data.getKm()));
        jsTrainRecordTime.setSubjcode(data.getClassNum());
        jsTrainRecordTime.setClassId(String.valueOf(data.getClassId()));
        jsTrainRecordTime.setStatus(data.getStatus());
        int timeId = jsTrainRecordTimeMapper.insertSelective(jsTrainRecordTime);
        //加入培训过程位置
        JsTrainLocationService jsTrainLocationService = BeanHelper.getBean(JsTrainLocationServiceImpl.class);
        jsTrainLocationService.insert(data.getT0200(),data.getHeader().getMobileNo(),0,timeId);

        return jsTrainRecordTime;
    }

    @Override
    public void getMenuList(short parentId,int times) {
        if(times >= 3) return;
        log.info("---------");
        List<SysMenu> sysMenuList = sysMenuMapper.selectByParentId(parentId);
        if(CollectionUtils.isNotEmpty(sysMenuList)){
            for(int i=0;i<sysMenuList.size();i++){
                SysMenu childMenu = sysMenuList.get(i);
                log.info("menuName:{}",childMenu.getMenuName());
                this.getMenuList(childMenu.getId(),times + 1);
            }
        }
    }
}