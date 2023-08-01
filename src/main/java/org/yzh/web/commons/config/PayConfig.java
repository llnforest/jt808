package org.yzh.web.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/11 11:19
 */
@Component
public class  PayConfig {
    @Value("${pay.abc.account}")
    public String abcAccount;

    @Value("${pay.abc.name}")
    public String abcName;

    @Value("${pay.abc.notifyUrl}")
    public String abcNotifyUrl;

    @Value("${pay.wx.appId}")
    public String wxAppId;

    @Value("${pay.wx.notifyUrl}")
    public String wxNotifyUrl;

    @Value("${pay.alipay.notifyUrl}")
    public String alipayNotifyUrl;

}
