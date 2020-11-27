package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsCoach;

@Repository
public interface JsCoachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsCoach record);

    int insertSelective(JsCoach record);

    JsCoach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsCoach record);

    int updateByPrimaryKey(JsCoach record);

    JsCoach isCoachLogin(String coachnum);


}