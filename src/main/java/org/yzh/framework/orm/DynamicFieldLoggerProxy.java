package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.apache.commons.lang3.ArrayUtils;

public class DynamicFieldLoggerProxy extends DynamicField {

    private DynamicField target;

    public DynamicFieldLoggerProxy(DynamicField target) {
        super(target.field, target.property);
        this.target = target;
    }

    @Override
    public Object readValue(ByteBuf buf, int length) {
        int before = buf.readerIndex();
        Object value = target.readValue(buf, length);
        int after = buf.readerIndex();

        String hex = ByteBufUtil.hexDump(buf.slice(before, after - before));
        System.out.println(index + "\t" + "[" + hex + "] " + desc + ": " + (value.getClass().isArray() ? ArrayUtils.toString(value) : value));
        return value;
    }

    @Override
    public void writeValue(ByteBuf buf, Object value) {
        int before = buf.writerIndex();
        target.writeValue(buf, value);
        int after = buf.writerIndex();

        String hex = ByteBufUtil.hexDump(buf, before, after - before);
        System.out.println(index + "\t" + "[" + hex + "] " + desc + ": " + value);
    }

    @Override
    public int compareTo(BasicField that) {
        return target.compareTo(that);
    }
}