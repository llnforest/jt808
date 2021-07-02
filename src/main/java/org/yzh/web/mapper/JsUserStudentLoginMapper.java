package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsUserStudentLogin;

@Repository
public interface JsUserStudentLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsUserStudentLogin record);

    int insertSelective(JsUserStudentLogin record);

    JsUserStudentLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsUserStudentLogin record);

    int updateByPrimaryKey(JsUserStudentLogin record);

    JsUserStudentLogin getLastRecord(String stunum);
}