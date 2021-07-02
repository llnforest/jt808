package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsUserStudent;

@Repository
public interface JsUserStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsUserStudent record);

    int insertSelective(JsUserStudent record);

    JsUserStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsUserStudent record);

    int updateByPrimaryKey(JsUserStudent record);

    JsUserStudent selectByStunum(String stunum);
}