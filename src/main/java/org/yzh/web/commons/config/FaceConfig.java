package org.yzh.web.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/11 11:19
 */
@Component
public class FaceConfig {
    public static String appId;

    public static String sdkKey;

    public static String libPath;

    public static String prePath;

    @Value("${face.hr.APP_ID}")
    public void setAppId(String appId) {
        FaceConfig.appId = appId;
    }
    @Value("${face.hr.APP_KEY}")
    public void setSdkKey(String sdkKey) {
        FaceConfig.sdkKey = sdkKey;
    }

    @Value("${face.hr.lib_path}")
    public void setLibPath(String libPath) {
        FaceConfig.libPath = libPath;
    }

    @Value("${face.hr.pre_path}")
    public void setPrePath(String prePath) {
        FaceConfig.prePath = prePath;
    }
}
