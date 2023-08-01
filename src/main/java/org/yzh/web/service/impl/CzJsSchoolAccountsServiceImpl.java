package org.yzh.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.DbUtils;
import org.yzh.web.mapper.CzJsSchoolAccountsMapper;
import org.yzh.web.model.entity.CzJsSchoolAccounts;
import org.yzh.web.service.CzJsSchoolAccountsService;

import java.util.List;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:40
 */
@Service
public class CzJsSchoolAccountsServiceImpl implements CzJsSchoolAccountsService {
    @Autowired
    private CzJsSchoolAccountsMapper czJsSchoolAccountsMapper;


    @Override
    public CzJsSchoolAccounts find(CzJsSchoolAccounts czJsSchoolAccounts) {
        return DbUtils.find(czJsSchoolAccountsMapper.select(czJsSchoolAccounts));
    }

    @Override
    public List<CzJsSchoolAccounts> select(CzJsSchoolAccounts czJsSchoolAccounts) {
        return czJsSchoolAccountsMapper.select(czJsSchoolAccounts);
    }

    @Override
    public CzJsSchoolAccounts selectByPrimaryKey(int primaryKey){
        return czJsSchoolAccountsMapper.selectByPrimaryKey(primaryKey);
    }
}
