package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.平台登录应答})
public class T8090 extends AbstractMessage<Header> {
    private int result_success = 0;//成功
    private int result_error_ip = 1;//IP地址不正确
    private int result_error_platcode = 2;//接入码不正确
    private int result_error_unregister = 3;//该平台没有注册
    private int result_error_platsercet = 4;//密码错误
    private int result_error_full = 5;//资源紧张，稍后再连接（已经占用）
    private int result_error_other = 9;//其他

    private int result;

    public T8090() {
    }


    /** 应答结果 */
    @Field(index = 0, type = DataType.BYTE, desc = "应答结果")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}