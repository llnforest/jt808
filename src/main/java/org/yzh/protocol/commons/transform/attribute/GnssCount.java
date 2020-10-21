package org.yzh.protocol.commons.transform.attribute;

import org.yzh.protocol.commons.transform.Attribute;

/**
 * GNSS 定位卫星数
 * length 1 BYTE
 */
public class GnssCount extends Attribute {

    public static final int attributeId = 0x31;
    private int value;

    public GnssCount() {
    }

    public GnssCount(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public GnssCount formBytes(byte... bytes) {
        this.value = bytes[0];
        return this;
    }

    @Override
    public byte[] toBytes() {
        return new byte[]{(byte) value};
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}