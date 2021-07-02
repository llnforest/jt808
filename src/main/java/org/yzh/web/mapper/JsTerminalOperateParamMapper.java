package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsTerminalOperateParam;

@Repository
public interface JsTerminalOperateParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsTerminalOperateParam record);

    int insertSelective(JsTerminalOperateParam record);

    JsTerminalOperateParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsTerminalOperateParam record);

    int updateByPrimaryKey(JsTerminalOperateParam record);

    int updateByStatus(JsTerminalOperateParam record);
}