package org.yzh.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.DbUtils;
import org.yzh.web.mapper.CzOrderRefundsMapper;
import org.yzh.web.model.entity.CzOrderRefunds;
import org.yzh.web.service.CzOrderRefundsService;

import java.util.List;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:40
 */
@Service
public class CzOrderRefundsServiceImpl implements CzOrderRefundsService {
    @Autowired
    private CzOrderRefundsMapper czOrderRefundsMapper;


    @Override
    public CzOrderRefunds find(CzOrderRefunds czOrderRefunds) {
        return DbUtils.find(czOrderRefundsMapper.select(czOrderRefunds));
    }

    @Override
    public List<CzOrderRefunds> select(CzOrderRefunds czOrderRefunds) {
        return czOrderRefundsMapper.select(czOrderRefunds);
    }

    @Override
    public CzOrderRefunds selectByPrimaryKey(int primaryKey){
        return czOrderRefundsMapper.selectByPrimaryKey(primaryKey);
    }
}
