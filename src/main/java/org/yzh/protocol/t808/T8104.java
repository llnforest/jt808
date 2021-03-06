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
@Message(JT808.查询终端参数)
public class T8104 extends AbstractMessage<Header> {

    public T8104() {
    }

    public T8104(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.查询终端参数.substring(2),16), serialNo, mobileNo));
    }

    public T8104(int serialNo, String mobileNo) {
        super(new Header(Integer.parseInt(JT808.查询终端参数.substring(2),16), serialNo, mobileNo));
    }
}