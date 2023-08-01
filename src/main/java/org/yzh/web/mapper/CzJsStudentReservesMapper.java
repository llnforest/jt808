package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.yzh.web.model.entity.CzJsStudentReserves;

@Mapper
@Component
public interface CzJsStudentReservesMapper extends BaseMapper<CzJsStudentReserves> {
}