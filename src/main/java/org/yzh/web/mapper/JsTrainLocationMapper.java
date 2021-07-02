package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTrainLocation;

@Repository
public interface JsTrainLocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTrainLocation record);

    int insertSelective(JsTrainLocation record);

    JsTrainLocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTrainLocation record);

    int updateByPrimaryKey(JsTrainLocation record);
}