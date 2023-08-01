package org.yzh.web.service;

import org.yzh.protocol.t808.T8900_0900_time_up;

public interface CzPlatConfigTimeChecksService {

    /**
     * 通过id获取配置值
     *
     * @param id id
     * @return float
     * @Description //TODO
     * @Author lynn 14:45 2022/12/7
     */
    float getConfigValueById(int id);


}