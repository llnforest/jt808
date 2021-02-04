package org.yzh.web.service;

import org.yzh.protocol.t808.T8900_0900_terminal_status_answer;

public interface TerminalStatusService {

    /**
     * 修改下发参数配置状态
     * @param phone
     * @param message
     * @return
     */
    boolean updateTerminalStatusStatus(String phone,Integer downStatus, T8900_0900_terminal_status_answer message);

}