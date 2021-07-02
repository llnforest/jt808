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
@Message({JT808.平台登录请求})
public class T01F0 extends AbstractMessage<Header> {

    private String platNum;
    private String platSecret;
    private int platCode;

    public T01F0() {
    }

    public T01F0(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.平台登录请求.substring(2),16), serialNo, mobileNo));
    }

    public T01F0(int serialNo, String mobileNo) {
        super(new Header(Integer.parseInt(JT808.平台登录请求.substring(2),16), serialNo, mobileNo));
    }

    /** 平台编号 */
    @Field(index = 0, type = DataType.BYTES, length = 5, desc = "平台编号")
    public String getPlatNum() {
        return platNum;
    }

    public void setPlatNum(String platNum) {
        this.platNum = platNum;
    }

    /** 密码 */
    @Field(index = 5, type = DataType.BYTES, length = 8, desc = "密码")
    public String getPlatSecret() {
        return platSecret;
    }

    public void setPlatSecret(String platSecret) {
        this.platSecret = platSecret;
    }

    /** 平台接入码 */
    @Field(index = 13, type = DataType.DWORD, desc = "平台接入码")
    public int getPlatCode() {
        return platCode;
    }

    public void setPlatCode(int platCode) {
        this.platCode = platCode;
    }
}