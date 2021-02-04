package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTerminalConfig;

@Repository
public interface JsTerminalConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTerminalConfig record);

    int insertSelective(JsTerminalConfig record);

    JsTerminalConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTerminalConfig record);

    int updateByPrimaryKeyWithBLOBs(JsTerminalConfig record);

    int updateByPrimaryKey(JsTerminalConfig record);

    int updateByStatus(JsTerminalConfig record);
}