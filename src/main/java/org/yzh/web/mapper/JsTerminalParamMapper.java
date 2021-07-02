package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTerminalParam;

@Repository
public interface JsTerminalParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTerminalParam record);

    int insertSelective(JsTerminalParam record);

    JsTerminalParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTerminalParam record);

    int updateByPrimaryKeyWithBLOBs(JsTerminalParam record);

    int updateByPrimaryKey(JsTerminalParam record);

    int updateByStatus(JsTerminalParam record);
}