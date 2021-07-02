package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTrainCar;

@Repository
public interface JsTrainCarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTrainCar record);

    int insertSelective(JsTrainCar record);

    JsTrainCar selectByPrimaryKey(Integer id);

    JsTrainCar find(JsTrainCar record);

    int updateByPrimaryKeySelective(JsTrainCar record);

    int updateByPrimaryKey(JsTrainCar record);
}