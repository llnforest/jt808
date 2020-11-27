package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsStudent;

@Repository
public interface JsStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsStudent record);

    int insertSelective(JsStudent record);

    JsStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsStudent record);

    int updateByPrimaryKey(JsStudent record);

    JsStudent selectByStunum(String stunum);
}