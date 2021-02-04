package org.yzh.framework.commons;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);


    /**
     * 判断是否是透传协议
     * @param messageId
     * @return
     */
    public static boolean isTransparent(int messageId){
        if(messageId == 2304 || messageId == 35072) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取标记id
     * @param messageId
     * @param bodyBuf
     * @return
     */
    public static String getMarkId(int messageId, ByteBuf bodyBuf){
        String markId = "0x"+ StringUtils.leftPad(Integer.toHexString(messageId),4,"0");

        if(isTransparent(messageId)) {
            markId = markId + "_0x" + ByteBufUtil.hexDump(bodyBuf).substring(2,6);

        }
        return markId;

    }




}
