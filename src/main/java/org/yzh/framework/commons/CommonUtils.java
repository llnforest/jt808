package org.yzh.framework.commons;

import com.mysql.cj.util.Base64Decoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.model.entity.CzJsDevices;
import org.yzh.web.service.CzJsDevicesService;
import org.yzh.web.sign.IVerify;
import org.yzh.web.sign.Verify;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);


    /**
     * 判断是否是透传协议
     *
     * @param messageId
     * @return
     */
    public static boolean isTransparent(int messageId) {
        if (messageId == 2304 || messageId == 35072) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取标记id
     *
     * @param messageId
     * @param bodyBuf
     * @return
     */
    public static String getMarkId(int messageId, ByteBuf bodyBuf) {
        String markId = "0x" + StringUtils.leftPad(Integer.toHexString(messageId), 4, "0");

        if (isTransparent(messageId)) {
            markId = markId + "_0x" + ByteBufUtil.hexDump(bodyBuf).substring(2, 6);

        }
        return markId;

    }

    public static boolean verifySign(String messageId, ByteBuf bodyBuf, String mobile) {
        if (true) return true;
        if (!messageId.startsWith("0x0900_")) return true;
        int length = bodyBuf.readableBytes();
        log.info("总长度：{}", length);
        if (length < 256) return false;
        byte[] paramBytes = new byte[length - 256];
        bodyBuf.readBytes(paramBytes);
        byte[] signBytes = new byte[256];
        bodyBuf.readBytes(signBytes);
//        ByteBuf signBuf = bodyBuf.slice(length-256,256);
//        ByteBuf paramBuf = bodyBuf.slice(0,length-256);
        log.info("paramBuf:{}", ByteBufUtil.hexDump(paramBytes));
        log.info("signBuf:{}", ByteBufUtil.hexDump(signBytes));
        CzJsDevicesService service = BeanHelper.getBean(CzJsDevicesService.class);
        CzJsDevices model = new CzJsDevices();
        model.setBindSim(mobile);
        CzJsDevices jsDevice = service.find(model);

        log.info("js:{}", jsDevice);
        if (jsDevice == null) {
            return false;
        }
        try {
            String cadata = jsDevice.getCert();
            char[] password = jsDevice.getPasswd().toCharArray();
            byte[] cabuf = Base64Decoder.decode(cadata.getBytes(), 0, cadata.length());
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new ByteArrayInputStream(cabuf), password);
            Enumeration<String> aliases = keyStore.aliases();
            if (!aliases.hasMoreElements()) {
                throw new RuntimeException("no alias found");
            }
            String alias = aliases.nextElement();
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
            IVerify verify = new Verify();
            boolean ok = verify.verify(paramBytes, signBytes, cert, "e2");
            return ok;
        } catch (Exception e) {
            log.info("异常：{},{}", e.getMessage(), e.getStackTrace());
            return false;
        }
    }

    public static ByteBuf getBodyBuf(String messageId, ByteBuf bodyBuf, int index) {
        if (!messageId.startsWith("0x0900_")) return bodyBuf;
        bodyBuf.readerIndex(index);
        ByteBuf buf = bodyBuf.slice(index, bodyBuf.readableBytes() - 256);
        bodyBuf.skipBytes(bodyBuf.readableBytes() - 256);
        return buf;

    }

    public static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
