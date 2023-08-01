package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.yzh.web.model.entity.CzJsSchoolAccounts;

@Mapper
@Component
public interface CzJsSchoolAccountsMapper extends BaseMapper<CzJsSchoolAccounts> {
}