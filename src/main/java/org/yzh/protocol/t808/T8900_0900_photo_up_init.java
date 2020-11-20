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
@Message({JT808.数据上行透传, JT808.数据下行透传})
public class T8900_0900_photo_up_init extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x0305;
    private int msgAttr;
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private String photoNum;
    private String studentOrCoachNum;
    private int upMode;
    private int channelNo;
    private int photoSize;
    private int eventType;
    private int totalPacket;
    private int photoDataSize;
    private int classId;
    private T0200 t0200;
    private int faceNum;


    public T8900_0900_photo_up_init() {
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

    @Field(index = 23, type = DataType.WORD, desc = "数据内容长度")
    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    @Field(index = 25, type = DataType.BYTES,length = 10,desc = "照片编号")
    public String getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(String photoNum) {
        this.photoNum = photoNum;
    }

    @Field(index = 35, type = DataType.BYTES,length = 16,desc = "学员或教练员编号")
    public String getStudentOrCoachNum() {
        return studentOrCoachNum;
    }

    public void setStudentOrCoachNum(String studentOrCoachNum) {
        this.studentOrCoachNum = studentOrCoachNum;
    }

    @Field(index = 41, type = DataType.BYTE,desc = "上传模式")
    public int getUpMode() {
        return upMode;
    }

    public void setUpMode(int upMode) {
        this.upMode = upMode;
    }

    @Field(index = 42, type = DataType.BYTE,desc = "摄像头通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 43, type = DataType.BYTE,desc = "图片尺寸")
    public int getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(int photoSize) {
        this.photoSize = photoSize;
    }

    @Field(index = 44, type = DataType.BYTE,desc = "发起图片的事件类型")
    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    @Field(index = 45, type = DataType.WORD,desc = "总包数")
    public int getTotalPacket() {
        return totalPacket;
    }

    public void setTotalPacket(int totalPacket) {
        this.totalPacket = totalPacket;
    }

    @Field(index = 47, type = DataType.DWORD,desc = "照片数据大小")
    public int getPhotoDataSize() {
        return photoDataSize;
    }

    public void setPhotoDataSize(int photoDataSize) {
        this.photoDataSize = photoDataSize;
    }

    @Field(index = 51, type = DataType.DWORD,desc = "课堂ID")
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Field(index = 55, type = DataType.OBJ,length = 38,desc = "GNSS数据包")
    public T0200 getT0200() {
        return t0200;
    }

    public void setT0200(T0200 t0200) {
        this.t0200 = t0200;
    }

    @Field(index = 93, type = DataType.BYTE,desc = "人脸识别置信度")
    public int getFaceNum() {
        return faceNum;
    }

    public void setFaceNum(int faceNum) {
        this.faceNum = faceNum;
    }
}