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
@Message({JT808.数据上行透传, JT808.数据下行透传})
public class T8900_0900 extends AbstractMessage<Header> {



    private int type = 0x13;
    private T8900_0900_content content;


    public T8900_0900() {
    }

    public T8900_0900(int type, T8900_0900_content content) {
        this.type = type;
        this.content = content;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "透传消息类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.OBJ, desc = "透传消息内容")
    public T8900_0900_content getContent() {
        return content;
    }

    public void setContent(T8900_0900_content content) {
        this.content = content;
    }
}