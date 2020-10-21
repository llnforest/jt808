package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bytes;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * IO 状态位，定义见表 32
 * length 2
 */
public class IoState extends Attribute {

    public static final int attributeId = 0x2a;
    private int value;

    public IoState() {
    }

    public IoState(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public IoState formBytes(byte... bytes) {
        this.value = Bytes.getInt16(bytes, 0);
        return this;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.setInt16(new byte[2], 0, value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}