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
@Message(JT808.查询指定终端参数)
public class T8106 extends AbstractMessage<Header> {

    private int total;
    private byte[] id;

    public T8106() {
    }

    public T8106(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.查询指定终端参数.substring(2),16), serialNo, mobileNo));
    }

    public T8106(int serialNo, String mobileNo) {
        super(new Header(Integer.parseInt(JT808.查询指定终端参数.substring(2),16), serialNo, mobileNo));
    }


    public T8106(String mobileNo, byte... id) {
        super(new Header(mobileNo, Integer.parseInt(JT808.查询指定终端参数.substring(2),16)));
        this.id = id;
        this.total = id.length/4;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "参数总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.BYTES, desc = "参数ID列表")
    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
        this.total = id.length/4;
    }
}