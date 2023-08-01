package org.yzh.web.service;

import org.yzh.web.model.entity.CzJsSchoolAccounts;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:35
 */
public interface CzJsSchoolAccountsService extends BaseService<CzJsSchoolAccounts> {
    CzJsSchoolAccounts selectByPrimaryKey(int id);
}
