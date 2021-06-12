package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * 基础消息编码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageEncoder {

    private static final Logger log = LoggerFactory.getLogger(MessageEncoder.class.getSimpleName());

    public MessageEncoder(String basePackage) {
        MessageHelper.initial(basePackage);
    }

    /** 转码 */
    public abstract ByteBuf escape(ByteBuf buf);

    /** 签名 */
    public abstract ByteBuf sign(ByteBuf buf);

    public ByteBuf encode(AbstractMessage message) {
        log.info("message:{}",message);
        AbstractHeader header = message.getHeader();
        int version = header.getVersionNo();
        BeanMetadata bodyMetadata = MessageHelper.getBeanMetadata(message.getClass(), version);
        ByteBuf bodyBuf;
        if (bodyMetadata != null) {
            bodyBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(bodyMetadata.getLength(), 5*1024*1024);
            bodyMetadata.encode(bodyBuf, message);
        } else {
            bodyBuf = Unpooled.EMPTY_BUFFER;
            log.info("未找到对应的BeanMetadata[{}]", message.getClass());
        }

        int bodyLen = bodyBuf.readableBytes();
        log.info("消息体长度：{}",bodyLen);
        if (bodyLen > 1000){
            log.info("消息体不能大于1000b,实际" + bodyLen + "b,开启分包");
            return bodyBuf;
        }
        return getAllBuf(bodyBuf,header);
//        header.setBodyLength(bodyLen);
//
//        BeanMetadata headMetadata = MessageHelper.getBeanMetadata(header.getClass(), version);
//        ByteBuf headerBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(headMetadata.getLength(), 2048);
//        log.info("ok1");
//        headMetadata.encode(headerBuf, header);
//        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);
//        log.info("ok2");
//        allBuf = sign(allBuf);
//        allBuf = escape(allBuf);
//        return allBuf;
    }

    public ByteBuf bodyEncode(AbstractMessage message) {
        AbstractHeader header = message.getHeader();
        int version = header.getVersionNo();
        BeanMetadata bodyMetadata = MessageHelper.getBeanMetadata(message.getClass(), version);
        ByteBuf bodyBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(bodyMetadata.getLength(), 5*1024*1024);
        bodyMetadata.encode(bodyBuf, message);
        return bodyBuf;
    }

    public ByteBuf packageEncode(ByteBuf bodyBuf,AbstractHeader header){
        return getAllBuf(bodyBuf,header);
    }

    public ByteBuf getAllBuf(ByteBuf bodyBuf,AbstractHeader header){
        int version = header.getVersionNo();
        int bodyLen = bodyBuf.readableBytes();
        header.setBodyLength(bodyLen);
        BeanMetadata headMetadata = MessageHelper.getBeanMetadata(header.getClass(), version);
        ByteBuf headerBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(headMetadata.getLength(), 5*1024*1024);
        headMetadata.encode(headerBuf, header);
        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);
        allBuf = sign(allBuf);
        allBuf = escape(allBuf);
        log.info("--报文：{}", ByteBufUtil.hexDump(allBuf));
        return allBuf;
    }

}