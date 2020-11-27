package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsClassrecordUp;

@Repository
public interface JsClassrecordUpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsClassrecordUp record);

    int insertSelective(JsClassrecordUp record);

    JsClassrecordUp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsClassrecordUp record);

    int updateByPrimaryKey(JsClassrecordUp record);
}