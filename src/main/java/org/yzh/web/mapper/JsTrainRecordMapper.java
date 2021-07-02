package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTrainRecord;

import java.util.List;

@Repository
public interface JsTrainRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTrainRecord record);

    int insertSelective(JsTrainRecord record);

    JsTrainRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTrainRecord record);

    int updateByPrimaryKey(JsTrainRecord record);

    JsTrainRecord selectByStunum(@Param("stunum") String stunum, @Param("status") int status);

    List<JsTrainRecord> select(JsTrainRecord record);
}