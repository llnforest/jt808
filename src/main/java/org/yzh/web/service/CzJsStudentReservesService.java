package org.yzh.web.service;

import org.yzh.web.model.entity.CzJsStudentReserves;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:35
 */
public interface CzJsStudentReservesService extends BaseService<CzJsStudentReserves> {
    CzJsStudentReserves selectByPrimaryKey(int id);

    int updateToken(int id, String token);
}
