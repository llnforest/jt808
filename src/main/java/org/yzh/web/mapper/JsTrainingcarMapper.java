package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTrainingcar;

import java.util.List;

@Repository
public interface JsTrainingcarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTrainingcar record);

    int insertSelective(JsTrainingcar record);

    JsTrainingcar selectByPrimaryKey(Integer id);

    JsTrainingcar find(JsTrainingcar record);

    int updateByPrimaryKeySelective(JsTrainingcar record);

    int updateByPrimaryKey(JsTrainingcar record);
}