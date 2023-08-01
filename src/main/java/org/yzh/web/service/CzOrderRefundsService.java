package org.yzh.web.service;

import org.yzh.web.model.entity.CzOrderRefunds;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:35
 */
public interface CzOrderRefundsService extends BaseService<CzOrderRefunds> {
    CzOrderRefunds selectByPrimaryKey(int id);
}
