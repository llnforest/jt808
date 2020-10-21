package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Fs;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.终端鉴权)
public class T0102 extends AbstractMessage<Header> {

    /** 终端重连后上报鉴权码 */
    private String token;
    private String imei;
    private String version;

    @Fs({@Field(index = 0, type = DataType.STRING, desc = "鉴权码", version = 0),
            @Field(index = 1, type = DataType.STRING, lengthSize = 1, desc = "鉴权码", version = 1)})
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Field(index = 2, type = DataType.STRING, length = 15, desc = "终端IMEI", version = 1)
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Field(index = 17, type = DataType.STRING, length = 20, desc = "软件版本号", version = 1)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}