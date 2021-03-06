package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.立即拍照})
public class T8900_0900_photo_command extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x8301;
    private int msgAttr = 2;//2需要应答(不加密) 0不需要应答(不加密) 10需要应答(SHA256加密)  8不需要应答(SHA256加密)
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private int upMode;
    private int channelNo;
    private int photoSize;


    public T8900_0900_photo_command() {
    }

    public T8900_0900_photo_command(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.数据下行透传.substring(2),16), serialNo, mobileNo));
    }

    public T8900_0900_photo_command(int serialNo, String mobileNo) {
        super(new Header(Integer.parseInt(JT808.数据下行透传.substring(2),16), serialNo, mobileNo));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "透传消息类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.WORD, desc = "透传消息Id")
    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    @Field(index = 3, type = DataType.WORD, desc = "扩展消息属性")
    public int getMsgAttr() {
        return msgAttr;
    }

    public void setMsgAttr(int msgAttr) {
        this.msgAttr = msgAttr;
    }

    @Field(index = 5, type = DataType.WORD, desc = "驾培包序号")
    public int getPacketNo() {
        return packetNo;
    }

    public void setPacketNo(int packetNo) {
        this.packetNo = packetNo;
    }

    @Field(index = 7, type = DataType.BYTES,length = 16,desc = "终端编号")
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    @Field(index = 23, type = DataType.DWORD, desc = "数据内容长度")
    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }


    @Field(index = 27, type = DataType.BYTE,desc = "上传模式")
    public int getUpMode() {
        return upMode;
    }

    public void setUpMode(int upMode) {
        this.upMode = upMode;
    }

    @Field(index = 28, type = DataType.BYTE,desc = "摄像头通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 29, type = DataType.BYTE,desc = "图片尺寸")
    public int getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(int photoSize) {
        this.photoSize = photoSize;
    }
}