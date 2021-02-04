package org.yzh.web.service;

public interface TerminalConfigService {

    /**
     * 修改下发参数配置状态
     * @param phone
     * @param status
     * @return
     */
    boolean updateTerminalConfigStatus(String phone,Integer status);

}