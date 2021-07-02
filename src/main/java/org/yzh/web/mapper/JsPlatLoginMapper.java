package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsPlatLogin;

@Repository
public interface JsPlatLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsPlatLogin record);

    int insertSelective(JsPlatLogin record);

    JsPlatLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsPlatLogin record);

    int updateByPrimaryKey(JsPlatLogin record);
}