package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTrainRecordTime;

@Repository
public interface JsTrainRecordTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTrainRecordTime record);

    int insertSelective(JsTrainRecordTime record);

    JsTrainRecordTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTrainRecordTime record);

    int updateByPrimaryKey(JsTrainRecordTime record);
}