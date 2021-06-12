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
    private long timeStamp;
    private byte[] token;

    @Field(index = 0, type = DataType.DWORD, desc = "时间戳")
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


    @Field(index = 4, type = DataType.BYTES, length = 256, desc = "鉴权密文")
    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

}