package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yzh.web.commons.DbUtils;
import org.yzh.web.mapper.CzFinanceDrawSchoolsMapper;
import org.yzh.web.mapper.CzFinancePlatsMapper;
import org.yzh.web.mapper.CzFinanceSchoolWithdrawsMapper;
import org.yzh.web.model.entity.CzFinanceDrawSchools;
import org.yzh.web.model.entity.CzFinancePlats;
import org.yzh.web.model.entity.CzFinanceSchoolWithdraws;
import org.yzh.web.service.CzFinanceDrawSchoolsService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:40
 */
@Service
public class CzFinanceDrawSchoolsServiceImpl implements CzFinanceDrawSchoolsService {
    private static final Logger log = LoggerFactory.getLogger(CzFinanceDrawSchoolsServiceImpl.class.getSimpleName());

    @Autowired
    private CzFinanceDrawSchoolsMapper czFinanceDrawSchoolsMapper;

    @Autowired
    private CzFinanceSchoolWithdrawsMapper czFinanceSchoolWithdrawsMapper;

    @Autowired
    private CzFinancePlatsMapper czFinancePlatsMapper;

    @Override
    public CzFinanceDrawSchools find(CzFinanceDrawSchools czFinanceDrawSchools) {
        return DbUtils.find(czFinanceDrawSchoolsMapper.select(czFinanceDrawSchools));
    }

    @Override
    public List<CzFinanceDrawSchools> select(CzFinanceDrawSchools czFinanceDrawSchools) {
        return czFinanceDrawSchoolsMapper.select(czFinanceDrawSchools);
    }

    @Override
    @Transactional
    public boolean cashResult(int code, CzFinanceDrawSchools czFinanceDrawSchools, BigDecimal applyFee, String bankNo, String remark) {
//        try {
        CzFinanceSchoolWithdraws czFinanceSchoolWithdraws = new CzFinanceSchoolWithdraws();
        czFinanceSchoolWithdraws.setInscode(czFinanceDrawSchools.getInscode());
        czFinanceSchoolWithdraws.setSchoolName(czFinanceDrawSchools.getSchoolName());
        czFinanceSchoolWithdraws.setWithdrawFee(applyFee);
        czFinanceSchoolWithdraws.setBankNo(bankNo);
        czFinanceSchoolWithdraws.setTradeAt(new Date());
        czFinanceSchoolWithdraws.setCreatedAt(new Date());
        czFinanceSchoolWithdraws.setUpdatedAt(new Date());
        czFinanceSchoolWithdraws.setRemark(remark);

        if (code == 1) {// 成功
            CzFinanceDrawSchools model = new CzFinanceDrawSchools();
            model.setId(czFinanceDrawSchools.getId());
            model.setApplyFee(czFinanceDrawSchools.getApplyFee().subtract(applyFee));
            log.info("可提现金额：{}", czFinanceDrawSchools.getApplyFee().subtract(applyFee));
            log.info("已提现金额：{}", czFinanceDrawSchools.getAppliedFee().add(applyFee));
            model.setAppliedFee(czFinanceDrawSchools.getAppliedFee().add(applyFee));
            czFinanceDrawSchoolsMapper.updateByPrimaryKeySelective(model);
            czFinanceSchoolWithdraws.setStatus(1);
            // 插入平台流水
            CzFinancePlats czFinancePlats = new CzFinancePlats();
            czFinancePlats.setInscode(czFinanceDrawSchools.getInscode());
            czFinancePlats.setSchoolName(czFinanceDrawSchools.getSchoolName());
            czFinancePlats.setObjnum(czFinanceDrawSchools.getInscode());
            czFinancePlats.setObjName(czFinanceDrawSchools.getSchoolName());
            czFinancePlats.setBankAccount(bankNo);
            czFinancePlats.setDealFee(applyFee);
            czFinancePlats.setDealType(2);
            czFinancePlats.setDealTarget(2);
            czFinancePlats.setDealStatus(2);
            czFinancePlats.setRemark("剩余可提现金额："+model.getApplyFee()+"元，已提现金额："+model.getAppliedFee()+"元");
            czFinancePlats.setCreatedAt(new Date());
            czFinancePlats.setUpdatedAt(new Date());
            czFinancePlatsMapper.insertSelective(czFinancePlats);
        } else {//失败
            czFinanceSchoolWithdraws.setStatus(2);
        }
        czFinanceSchoolWithdrawsMapper.insertSelective(czFinanceSchoolWithdraws);
//        } catch (Exception exception) {
//            log.info("提现成功后账单处理异常：{}", exception.getMessage());
//        }
        return true;
    }
}
