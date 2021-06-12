package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础消息编码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageEncoderWrapper extends MessageToByteEncoder<AbstractMessage> {

    private static final Logger log = LoggerFactory.getLogger(MessageEncoderWrapper.class.getSimpleName());

    private MessageEncoder encoder;

    private byte[] delimiter;

    public MessageEncoderWrapper(MessageEncoder encoder, byte[] delimiter) {
        this.encoder = encoder;
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) {
        if(msg.getHeader().isSubpackage()){
            //需要分包
            int length = 1000;
            ByteBuf buf = encoder.bodyEncode(msg);
            Integer packageNo = msg.getHeader().getPackageNo();
            Integer packageTotal = msg.getHeader().getPackageTotal();

            ByteBuf packageBuf = buf.slice((packageNo-1)*length,msg.getHeader().getBodyLength());
            ByteBuf pBuf = encoder.packageEncode(packageBuf,msg.getHeader());
            log.info("包总数：{}，包：{}",packageTotal,packageNo);
            out.writeBytes(delimiter).writeBytes(pBuf).writeBytes(delimiter);
        }else{
            //不需要分包
            ByteBuf buf = encoder.encode(msg);
            log.info("<<<<<原始报文[ip={}],hex={}", ctx.channel().remoteAddress(), ByteBufUtil.hexDump(buf));
            out.writeBytes(delimiter).writeBytes(buf).writeBytes(delimiter);
        }
//        ByteBuf buf = encoder.encode(msg);
//        int length = 1000;
//        if(buf.readableBytes() > length + 20){
//            int packageTotal = (int)Math.ceil((double) buf.readableBytes()/length);
//            msg.getHeader().setSubpackage(true);
//            msg.getHeader().setPackageTotal(packageTotal);
//            for(int packageNo = 1;packageNo <= packageTotal;packageNo ++){
////                out = null;
//                buf = encoder.bodyEncode(msg);
//                ByteBuf packageBuf = buf.slice((packageNo-1)*length,(buf.readableBytes() - length*(packageNo-1))>length?length:(buf.readableBytes() - length*(packageNo-1)));
//                ByteBuf pBuf = encoder.packageEncode(packageBuf,msg.getHeader());
//                log.info("包总数：{}，包：{}",packageTotal,packageNo);
//                out.writeBytes(delimiter).writeBytes(pBuf).writeBytes(delimiter);
//                ByteBuf bf = Unpooled.buffer(1024);
//                log.info("delimiter:{}",delimiter);
//                pBuf.writeBytes(delimiter);
//                log.info("发送报文：",ByteBufUtil.hexDump(pBuf));
//                log.info("发送报文2：",out);
//            }


//            for(int packageNo = 1;packageNo <= packageTotal;packageNo ++){
//                log.info("come1 in:{}",packageNo);
//                msg.getHeader().setPackageNo(packageNo);
//                log.info("全部字节：{}",buf);
//                log.info("come2 in:{}",packageNo);
//                log.info("计算1：{}",(packageNo-1)*1003);
//                log.info("计算2：{}",(buf.readableBytes() - 1003*(packageNo-1))>1003?1003:(buf.readableBytes() - 1003*(packageNo-1)));
//                ByteBuf packageBuf = buf.slice((packageNo-1)*1003,(buf.readableBytes() - 1003*(packageNo-1))>1003?1003:(buf.readableBytes() - 1003*(packageNo-1)));
//                log.info("全部字节2：{}",buf);
//                log.info("come3 in:{}",packageNo);
//                log.info("包字节：{}",ByteBufUtil.hexDump(packageBuf));
//                ByteBuf pBuf = encoder.packageEncode(packageBuf,msg.getHeader());
//                log.info("分包报文：{}",ByteBufUtil.hexDump(pBuf));
//                log.info("包总数：{}，包：{}",packageTotal,packageNo);
//                out.writeBytes(delimiter).writeBytes(pBuf).writeBytes(delimiter);
//            }
//        }else{
//            log.info("<<<<<原始报文[ip={}],hex={}", ctx.channel().remoteAddress(), ByteBufUtil.hexDump(buf));
//            out.writeBytes(delimiter).writeBytes(buf).writeBytes(delimiter);
//
//        }
    }
}