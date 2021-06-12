package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.SysMenu;

import java.util.List;

@Repository
public interface SysMenuMapper {
    int deleteByPrimaryKey(Short id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Short id);

    List<SysMenu> selectByParentId(Short parent_id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
}