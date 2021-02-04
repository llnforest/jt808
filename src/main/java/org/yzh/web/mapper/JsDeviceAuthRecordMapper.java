package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsDeviceAuthRecord;

import java.util.List;

@Repository
public interface JsDeviceAuthRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsDeviceAuthRecord record);

    int insertSelective(JsDeviceAuthRecord record);

    JsDeviceAuthRecord selectByPrimaryKey(Integer id);

    List<JsDeviceAuthRecord> selectByCondition(JsDeviceAuthRecord record);
    List<JsDeviceAuthRecord> selectRecordAndDevice(Integer id);

    int updateByPrimaryKeySelective(JsDeviceAuthRecord record);

    int updateByPrimaryKey(JsDeviceAuthRecord record);

}