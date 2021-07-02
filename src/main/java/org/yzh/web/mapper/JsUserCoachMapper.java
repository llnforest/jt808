package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsUserCoach;

@Repository
public interface JsUserCoachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsUserCoach record);

    int insertSelective(JsUserCoach record);

    JsUserCoach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsUserCoach record);

    int updateByPrimaryKey(JsUserCoach record);

    JsUserCoach isCoachLogin(String coachnum);


}