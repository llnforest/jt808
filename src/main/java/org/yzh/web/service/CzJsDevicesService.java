package org.yzh.web.service;

import org.yzh.web.model.entity.CzJsDevices;
import org.yzh.web.model.entity.CzOrderRefunds;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:35
 */
public interface CzJsDevicesService extends BaseService<CzJsDevices> {
    void updateCarStatus(String terminalNo, int isBehind,String location);
    void updateCarStatus(String plateNo,String location);
    void testThread();
}
