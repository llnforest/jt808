package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.yzh.web.model.entity.CzOrderRefunds;

@Mapper
@Component
public interface CzOrderRefundsMapper extends BaseMapper<CzOrderRefunds> {

}