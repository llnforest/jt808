package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.CommonUtils;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.RawMessage;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.codec.JTMessageDecoder;

/**
 * 基础消息解码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageDecoder {

    private SessionManager sessionManager;

    public MessageDecoder(String basePackage) {
        MessageHelper.initial(basePackage);
    }

    public MessageDecoder(String basePackage, SessionManager sessionManager) {
        this(basePackage);
        this.sessionManager = sessionManager;
    }

    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class.getSimpleName());

    private MultiPacketManager multiPacketManager = MultiPacketManager.getInstance();

    /** 转码 */
    public abstract ByteBuf unescape(ByteBuf buf);

    /** 校验 */
    public abstract boolean verify(ByteBuf buf);

    public AbstractMessage decode(ByteBuf buf) {
        return decode(buf, 0);
    }

    public AbstractMessage decode(ByteBuf buf, int version) {
        buf = unescape(buf);
        log.info("收到：{}" + ByteBufUtil.hexDump(buf));
        boolean verified = verify(buf);
        if (!verified)
            log.error("校验码错误" + ByteBufUtil.hexDump(buf));
        buf = buf.slice(0, buf.readableBytes() - 1);

        Class<? extends AbstractHeader> headerClass = (Class<? extends AbstractHeader>) MessageHelper.getHeaderClass();
        BeanMetadata<? extends AbstractHeader> headMetadata = MessageHelper.getBeanMetadata(headerClass, version);
        int readerIndex = buf.readerIndex();
        AbstractHeader header = headMetadata.decode(buf);
        log.info("header:{}",header);
        //如果头部有版本
//        if (header.isVersion()) {
//            buf.readerIndex(readerIndex);
//            headMetadata = MessageHelper.getBeanMetadata(headerClass, 0);
//            header = headMetadata.decode(buf);
//            version = header.getVersionNo();
//        }
        header.setVerified(verified);

        if (sessionManager != null) {
            Integer v = sessionManager.getVersion(header.getClientId());
            if (v != null) {
                version = v;
            }
        }

        AbstractMessage message;

        int headLen = header.getHeadLength();
        int bodyLen = header.getBodyLength();
        buf.readerIndex(headLen);
        ByteBuf buf1 = buf.slice(0,10);

//        log.info("分包：{}",header.isSubpackage());
        if (header.isSubpackage()) {//有分包
            log.info("有分包");
            byte[] bytes = new byte[bodyLen];
            buf.readBytes(bytes);

            byte[][] packages = multiPacketManager.addAndGet(header, bytes);
            if (packages == null)
                return null;

            ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
            String messageId = CommonUtils.getMarkId(header.getMessageId(),bodyBuf);
            if(!CommonUtils.verifySign(messageId,bodyBuf,header.getMobileNo())){
                return null;
            }
            BeanMetadata<? extends AbstractMessage> bodyMetadata = MessageHelper.getBeanMetadata(messageId, version);
            if(bodyMetadata != null){
                message = bodyMetadata.decode(CommonUtils.getBodyBuf(messageId,bodyBuf,0));
            }else{
                message = new RawMessage<>();
                log.info("未找到对应的BeanMetadata[{}]", header);
            }
            message.setMarkId(messageId);
        } else {//无分包
            log.info("无分包");
            String messageId = CommonUtils.getMarkId(header.getMessageId(),buf);
            if(!CommonUtils.verifySign(messageId,buf,header.getMobileNo())){
                return null;
            }
            BeanMetadata<? extends AbstractMessage> bodyMetadata = MessageHelper.getBeanMetadata(messageId, version);
            log.info("bodyMetaData:{}",bodyMetadata);
            if(bodyMetadata != null) {
                message = bodyMetadata.decode(CommonUtils.getBodyBuf(messageId,buf,headLen));
            }else{
                message = new RawMessage<>();
                log.info("未找到对应的BeanMetadata[{}]", header);
            }
            message.setMarkId(messageId);
        }
        log.info("message:{}",message);
        message.setHeader(header);
        return message;
    }
}