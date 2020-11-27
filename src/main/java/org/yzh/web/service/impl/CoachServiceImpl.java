package org.yzh.web.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T8900_0900_coach_login;
import org.yzh.protocol.t808.T8900_0900_coach_logout;
import org.yzh.web.commons.StringUtil;
import org.yzh.web.mapper.JsCoachMapper;
import org.yzh.web.model.entity.JsCoach;
import org.yzh.web.service.CoachService;

@Service
public class CoachServiceImpl implements CoachService {

    private static final Logger log = LoggerFactory.getLogger(CoachServiceImpl.class.getSimpleName());

    @Autowired
    private JsCoachMapper jsCoachMapper;


    @Override
    public int coachLogin(T8900_0900_coach_login request) {
        try {
            String coachnum = request.getCoachNo();
            if (StringUtils.isEmpty(coachnum)) {
                return 2;//无效的教练员编号
            }
            JsCoach jsCoach = jsCoachMapper.isCoachLogin(coachnum);
            if (jsCoach == null) {
                return 2;//无效的教练员编号
            } else if (jsCoach.getTeachpermitted().equals(request.getCoachType())) {
                return 3;//准教车型不符
            }
            //登录成功修改教练状态
            jsCoach.setStatus(1);
            jsCoachMapper.updateByPrimaryKey(jsCoach);
            return 1;//成功

        }catch (Exception e){
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
            JsCoach jsCoach = jsCoachMapper.isCoachLogin(coachnum);
            if (jsCoach == null) {
                return 2;//登出失败
            }
            //登录成功修改教练状态
            jsCoach.setStatus(0);
            jsCoachMapper.updateByPrimaryKey(jsCoach);
            return 1;//成功

        }catch (Exception e){
            return 9;
        }

    }
}