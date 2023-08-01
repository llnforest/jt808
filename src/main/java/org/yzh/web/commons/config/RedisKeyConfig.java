package org.yzh.web.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Star
 * @description: TODO
 * @date 2022/12/8 9:15
 */
@Component
public class RedisKeyConfig {
    public static String studentPhoto;
    public static String configTimeCheck;


    @Value("${redisKey.studentPhoto}")
    public void setStudentPhoto(String studentPhoto) {
        RedisKeyConfig.studentPhoto = studentPhoto;
    }

    @Value("${redisKey.configTimeCheck}")
    public void setConfigTimeCheck(String configTimeCheck) {
        RedisKeyConfig.configTimeCheck = configTimeCheck;
    }
}
