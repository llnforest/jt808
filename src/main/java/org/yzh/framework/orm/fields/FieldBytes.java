package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldBytes extends BasicField<byte[]> {

    public FieldBytes(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public byte[] readValue(ByteBuf buf, int length) {
        if (length < 0)
            length = buf.readableBytes();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return bytes;
    }

    @Override
    public void writeValue(ByteBuf buf, byte[] value) {
        if (length < 0)
            buf.writeBytes(value);
        else
            buf.writeBytes(value, 0, length);
    }
}