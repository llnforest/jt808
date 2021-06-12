package org.yzh.framework.orm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 消息基类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class AbstractHeader {
    private boolean verified = true;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /** 消息类型 */
    public abstract int getMessageId();
    /** 消息版本号 */
    public abstract int getVersionNo();
    /** 客户端唯一标识 */
    public abstract String getClientId();
    /** 消息流水号 */
    public abstract int getSerialNo();
    /** 保留位 */
    public abstract int getReserved();
    public abstract void setSerialNo(int serialNo);
    /** 消息头长度 */
    public abstract int getHeadLength();
    /** 消息体长度 */
    public abstract int getBodyLength();
    /** 获取手机号 */
    public abstract String getMobileNo();

    public abstract void setBodyLength(int bodyLength);
    /** 加密方式 */
    public abstract int getEncryption();
    /** 是否分包 */
    public abstract boolean isSubpackage();
    public abstract void setSubpackage(boolean subpackage);
    /** 是否有版本 */
    public abstract boolean isVersion();
    /** 包总数 */
    public abstract Integer getPackageTotal();
    public abstract void setPackageTotal(Integer packageTotal);
    /** 包序号 */
    public abstract Integer getPackageNo();
    public abstract void setPackageNo(Integer packageNo);

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}