package org.yzh.protocol.commons.transform.attribute;

import org.yzh.protocol.commons.transform.Attribute;

/**
 * 胎压
 * length 30
 */
public class TirePressure extends Attribute {

    public static final int attributeId = 0x05;
    private byte[] value;

    public TirePressure() {
    }

    public TirePressure(byte... value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public TirePressure formBytes(byte... bytes) {
        this.value = bytes;
        return this;
    }

    @Override
    public byte[] toBytes() {
        return value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}