package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTerminalStatus;

@Repository
public interface JsTerminalStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTerminalStatus record);

    int insertSelective(JsTerminalStatus record);

    JsTerminalStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTerminalStatus record);

    int updateByPrimaryKey(JsTerminalStatus record);

    int updateByStatus(JsTerminalStatus record);

    int updateByStatusOnly(JsTerminalStatus record);
}