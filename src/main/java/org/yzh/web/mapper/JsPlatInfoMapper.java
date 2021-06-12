package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsPlatInfo;

@Repository
public interface JsPlatInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsPlatInfo record);

    int insertSelective(JsPlatInfo record);

    JsPlatInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsPlatInfo record);

    int updateByPrimaryKey(JsPlatInfo record);
}