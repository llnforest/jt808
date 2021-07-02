package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsUserCoachLogin;

@Repository
public interface JsUserCoachLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsUserCoachLogin record);

    int insertSelective(JsUserCoachLogin record);

    JsUserCoachLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsUserCoachLogin record);

    int updateByPrimaryKey(JsUserCoachLogin record);

    JsUserCoachLogin getLastRecord(String coachnum);
}