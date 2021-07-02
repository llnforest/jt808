package org.yzh.web.service;

public interface JsTerminalOperateParamService {

    /**
     * 修改下发参数配置状态
     * @param phone
     * @param status
     * @return
     */
    boolean updateTerminalAppConfigStatus(String phone, Integer status);

}