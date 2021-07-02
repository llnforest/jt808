package org.yzh.web.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T8900_0900_coach_login;
import org.yzh.protocol.t808.T8900_0900_coach_logout;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.mapper.JsUserCoachLoginMapper;
import org.yzh.web.mapper.JsUserCoachMapper;
import org.yzh.web.model.entity.JsUserCoach;
import org.yzh.web.model.entity.JsUserCoachLogin;
import org.yzh.web.service.JsTrainLocationService;
import org.yzh.web.service.JsUserCoachService;

import java.util.Date;

@Service
public class JsUserCoachServiceImpl implements JsUserCoachService {

    private static final Logger log = LoggerFactory.getLogger(JsUserCoachServiceImpl.class.getSimpleName());

    @Autowired
    private JsUserCoachMapper jsUserCoachMapper;

    @Autowired
    private JsUserCoachLoginMapper jsUserCoachLoginMapper;


    @Override
    public int coachLogin(T8900_0900_coach_login request) {
        try {
            String coachnum = request.getCoachNo();
            log.info("coachnum:{}",coachnum);
            if (StringUtils.isEmpty(coachnum)) {
                return 2;//无效的教练员编号
            }
            JsUserCoach jsCoach = jsUserCoachMapper.isCoachLogin(coachnum);
            if (jsCoach == null) {
                return 2;//无效的教练员编号
            } else if (!jsCoach.getTeachpermitted().equals(request.getCoachType())) {
                return 3;//准教车型不符
            }
            //登录成功修改教练状态
            jsCoach.setStatus(1);
            jsUserCoachMapper.updateByPrimaryKeySelective(jsCoach);
            //添加登录记录
            JsUserCoachLogin record = new JsUserCoachLogin();
            record.setCoachnum(coachnum);
            record.setLoginTime(new Date());
            int loginId = jsUserCoachLoginMapper.insertSelective(record);

            //加入培训过程位置
            JsTrainLocationService jsTrainLocationService = BeanHelper.getBean(JsTrainLocationServiceImpl.class);
            jsTrainLocationService.insert(request.getT0200(),request.getHeader().getMobileNo(),4,loginId);
            return 1;//成功

        }catch (Exception e){
            log.info("Exception:{}",e);
            return 9;
        }

    }

    @Override
    public int coachLogout(T8900_0900_coach_logout request) {
        try {
            String coachnum = request.getCoachNo();
            if (StringUtils.isEmpty(coachnum)) {
                return 9;//无效的教练员编号
            }
            JsUserCoach jsCoach = jsUserCoachMapper.isCoachLogin(coachnum);
            if (jsCoach == null) {
                return 2;//登出失败
            }
            //登录成功修改教练状态
            jsCoach.setStatus(0);
            jsUserCoachMapper.updateByPrimaryKeySelective(jsCoach);
            //修改登录记录
            JsUserCoachLogin record = jsUserCoachLoginMapper.getLastRecord(coachnum);
            record.setLogoutTime(new Date());
            jsUserCoachLoginMapper.updateByPrimaryKeySelective(record);
            //加入培训过程位置
            JsTrainLocationService jsTrainLocationService = BeanHelper.getBean(JsTrainLocationServiceImpl.class);
            jsTrainLocationService.insert(request.getT0200(),request.getHeader().getMobileNo(),4,record.getId());
            return 1;//成功

        }catch (Exception e){
            log.info("Exception:{}",e.getMessage());
            return 9;
        }

    }
}