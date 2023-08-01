package org.yzh.web.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/11 11:19
 */
@Component
public class CommonConfig {
    public static String signKey;

    @Value("${sign.key}")
    public void setSignKey(String signKey) {
        CommonConfig.signKey = signKey;
    }
}
