package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTerminalAppConfig;

@Repository
public interface JsTerminalAppConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTerminalAppConfig record);

    int insertSelective(JsTerminalAppConfig record);

    JsTerminalAppConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTerminalAppConfig record);

    int updateByPrimaryKey(JsTerminalAppConfig record);

    int updateByStatus(JsTerminalAppConfig record);
}