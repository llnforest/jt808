package org.yzh.web.service;

import org.yzh.web.model.entity.CzFinanceDrawSchools;

import java.math.BigDecimal;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:35
 */
public interface CzFinanceDrawSchoolsService extends BaseService<CzFinanceDrawSchools> {
    boolean cashResult(int code, CzFinanceDrawSchools czFinanceDrawSchools, BigDecimal applyFee, String bankNo, String remark);
}
