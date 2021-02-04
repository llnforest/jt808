package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsStudentLoginRecord;

@Repository
public interface JsStudentLoginRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsStudentLoginRecord record);

    int insertSelective(JsStudentLoginRecord record);

    JsStudentLoginRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsStudentLoginRecord record);

    int updateByPrimaryKey(JsStudentLoginRecord record);

    JsStudentLoginRecord getLastRecord(String stunum);
}