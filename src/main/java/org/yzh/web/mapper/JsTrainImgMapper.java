package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTrainImg;

@Repository
public interface JsTrainImgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTrainImg record);

    int insertSelective(JsTrainImg record);

    JsTrainImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTrainImg record);

    int updateByPrimaryKey(JsTrainImg record);
}