package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsPlatLoginRecord;

@Repository
public interface JsPlatLoginRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsPlatLoginRecord record);

    int insertSelective(JsPlatLoginRecord record);

    JsPlatLoginRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsPlatLoginRecord record);

    int updateByPrimaryKey(JsPlatLoginRecord record);
}