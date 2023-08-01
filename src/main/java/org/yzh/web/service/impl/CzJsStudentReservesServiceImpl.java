package org.yzh.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.DbUtils;
import org.yzh.web.mapper.CzJsStudentReservesMapper;
import org.yzh.web.model.entity.CzJsStudentReserves;
import org.yzh.web.service.CzJsStudentReservesService;

import java.util.List;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:40
 */
@Service
public class CzJsStudentReservesServiceImpl implements CzJsStudentReservesService {
    @Autowired
    private CzJsStudentReservesMapper czJsStudentReservesMapper;


    @Override
    public CzJsStudentReserves find(CzJsStudentReserves czJsStudentReserves) {
        return DbUtils.find(czJsStudentReservesMapper.select(czJsStudentReserves));
    }

    @Override
    public List<CzJsStudentReserves> select(CzJsStudentReserves czJsStudentReserves) {
        return czJsStudentReservesMapper.select(czJsStudentReserves);
    }

    @Override
    public CzJsStudentReserves selectByPrimaryKey(int primaryKey) {
        return czJsStudentReservesMapper.selectByPrimaryKey(primaryKey);
    }

    @Override
    public int updateToken(int id, String token) {
        CzJsStudentReserves model = new CzJsStudentReserves();
        model.setId(id);
        model.setPayToken(token);
        return czJsStudentReservesMapper.updateByPrimaryKeySelective(model);
    }
}
