package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.CAN总线数据上传)
public class T0705 extends AbstractMessage<Header> {

    private int total;
    private String dateTime;
    private List<Item> items;

    @Field(index = 0, type = DataType.WORD, desc = "数据项个数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.BCD8421, length = 5, desc = "CAN 总线数据接收时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 7, type = DataType.LIST, desc = "CAN 总线数据项")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {
        private byte[] id;
        private byte[] data;

        public Item() {
        }

        public Item(byte[] id, byte[] data) {
            this.id = id;
            this.data = data;
        }

        @Field(index = 0, type = DataType.BYTES, length = 4, desc = "CAN ID")
        public byte[] getId() {
            return id;
        }

        public void setId(byte[] id) {
            this.id = id;
        }

        @Field(index = 4, type = DataType.BYTES, length = 8, desc = "CAN DATA")
        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }
}