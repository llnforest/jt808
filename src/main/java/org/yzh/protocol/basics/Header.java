package org.yzh.protocol.basics;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Fs;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.commons.MessageId;
import sun.misc.Version;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Header extends AbstractHeader {

    /** 协议版本号 */
    protected int versionNo = 0;
    /** 消息ID */
    protected int messageId;
    /** 消息体属性 */
    protected int properties;
    /** 手机号 */
    protected String mobileNo;
    /** 消息序列号 */
    protected int serialNo;
    /** 保留位 */
    protected int reserved;

    /** 包总数 */
    protected Integer packageTotal;
    /** 包序号 */
    protected Integer packageNo;

    public Header() {
    }

    public Header(int messageId, String mobileNo) {
        this.messageId = messageId;
        this.mobileNo = mobileNo;
    }

    public Header(int messageId, int serialNo, String mobileNo) {
        this.messageId = messageId;
        this.serialNo = serialNo;
        this.mobileNo = mobileNo;
    }

    public Header(String mobileNo, int messageId) {
        this.messageId = messageId;
        this.mobileNo = mobileNo;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "协议版本号")
    @Override
    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    @Field(index = 1, type = DataType.WORD, desc = "消息ID")
    @Override
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Field(index = 3, type = DataType.WORD, desc = "消息体属性")
    public int getProperties() {
        return properties;
    }

    public void setProperties(int properties) {
        this.properties = properties;
    }



    @Field(index = 5, type = DataType.BCD8421, length = 8, desc = "终端手机号")
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    @Field(index = 13, type = DataType.WORD, desc = "流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 15, type = DataType.BYTE, desc = "预留")
    @Override
    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }


    @Field(index = 16, type = DataType.WORD, desc = "消息包总数")
    @Override
    public Integer getPackageTotal() {
        if (isSubpackage())
            return packageTotal;
        return null;
    }

    public void setPackageTotal(Integer packageTotal) {
        this.packageTotal = packageTotal;
    }


    @Field(index = 18, type = DataType.WORD, desc = "包序号")
    @Override
    public Integer getPackageNo() {
        if (isSubpackage())
            return packageNo;
        return null;
    }

    public void setPackageNo(Integer packageNo) {
        this.packageNo = packageNo;
    }

    /** 消息头长度 */
    @Override
    public int getHeadLength() {
        if (isVersion())
            return isSubpackage() ? 20 : 16;
        return isSubpackage() ? 20 : 16;
    }

    private static final int BODY_LENGTH = 0b0000_0011_1111_1111;
    private static final int ENCRYPTION = 0b00011_100_0000_0000;
    private static final int SUBPACKAGE = 0b0010_0000_0000_0000;
    private static final int VERSION = 0b0100_0000_0000_0000;
    private static final int RESERVED = 0b1000_0000_0000_0000;

    /** 消息体长度 */
    @Override
    public int getBodyLength() {
        return this.properties & BODY_LENGTH;
    }

    public void setBodyLength(int bodyLength) {
        this.properties ^= (properties & BODY_LENGTH);
        this.properties |= bodyLength;
    }

    /** 加密方式 */
    @Override
    public int getEncryption() {
        return (properties & ENCRYPTION) >> 10;
    }

    public void setEncryption(int encryption) {
        this.properties ^= (properties & ENCRYPTION);
        this.properties |= (encryption << 10);
    }

    /** 是否分包 */
    @Override
    public boolean isSubpackage() {
        return (properties & SUBPACKAGE) == SUBPACKAGE;
    }

    public void setSubpackage(boolean subpackage) {
        if (subpackage)
            this.properties |= SUBPACKAGE;
        else
            this.properties ^= (properties & SUBPACKAGE);
    }

    /** 是否有版本 */
    @Override
    public boolean isVersion() {
        return (properties & VERSION) == VERSION;
    }

    public void setVersion(boolean version) {
        if (version)
            this.properties |= VERSION;
        else
            this.properties ^= (properties & VERSION);
    }

    /** 保留位 */
//    public boolean isReserved() {
//        return (properties & RESERVED) == RESERVED;
//    }
//
//    public void setReserved(boolean reserved) {
//        if (reserved)
//            this.properties |= RESERVED;
//        else
//            this.properties ^= (properties & RESERVED);
//    }

    @Override
    public String getClientId() {
        return mobileNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(102);
        sb.append(MessageId.get(messageId));
        sb.append('[');
        sb.append("versionNo=").append(versionNo);
        sb.append(", messageId=").append(messageId);
        sb.append(", properties=").append(properties);
        sb.append(", mobileNo=").append(mobileNo);
        sb.append(", serialNo=").append(serialNo);
        sb.append(", reserved=").append(reserved);
        if (isSubpackage()) {
            sb.append(", packageTotal=").append(packageTotal);
            sb.append(", packageNo=").append(packageNo);
        }
        sb.append(']');
        return sb.toString();
    }
}