package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.终端升级结果通知)
public class T0108 extends AbstractMessage<Header> {

    /** 终端 */
    public static final int Terminal = 0;
    /** 道路运输证IC卡 读卡器 */
    public static final int CardReader = 12;
    /** 北斗卫星定位模块 */
    public static final int Beidou = 52;

    private int type;
    private int result;

    @Field(index = 0, type = DataType.BYTE, desc = "升级类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "升级结果")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}