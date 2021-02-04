package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsCoachLoginRecord;

@Repository
public interface JsCoachLoginRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsCoachLoginRecord record);

    int insertSelective(JsCoachLoginRecord record);

    JsCoachLoginRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsCoachLoginRecord record);

    int updateByPrimaryKey(JsCoachLoginRecord record);

    JsCoachLoginRecord getLastRecord(String coachnum);
}