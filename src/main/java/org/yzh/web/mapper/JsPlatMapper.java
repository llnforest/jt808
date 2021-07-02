package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsPlat;

@Repository
public interface JsPlatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsPlat record);

    int insertSelective(JsPlat record);

    JsPlat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsPlat record);

    int updateByPrimaryKey(JsPlat record);
}