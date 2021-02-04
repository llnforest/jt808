package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsClassrecord;

@Repository
public interface JsClassrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsClassrecord record);

    int insertSelective(JsClassrecord record);

    JsClassrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsClassrecord record);

    int updateByPrimaryKey(JsClassrecord record);

    JsClassrecord selectByStunum(@Param("stunum") String stunum,@Param("status") int status);
}