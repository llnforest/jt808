package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldObject<T> extends BasicField<T> {

    protected BeanMetadata<T> beanMetadata;

    public FieldObject(Field field, PropertyDescriptor property, BeanMetadata<T> beanMetadata) {
        super(field, property);
        this.beanMetadata = beanMetadata;
    }

    @Override
    public T readValue(ByteBuf buf, int length) {
        if (length > 0)
            buf = buf.readSlice(length);
        return beanMetadata.decode(buf);
    }

    @Override
    public void writeValue(ByteBuf buf, T obj) {
        beanMetadata.encode(buf, obj);
    }
}