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
@Message({JT808.设置计时终端应用参数})
public class T8900_0900_terminal_set extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x8501;
    private int msgAttr = 2;//2需要应答(不加密) 0不需要应答(不加密) 10需要应答(SHA256加密)  8不需要应答(SHA256加密)
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private int paramNo;
    private int photoTime;
    private int upSet;
    private int isReadAdd;
    private int classDelayTime;
    private int upTime;
    private int coachDelayTime;
    private int verifyTime;
    private int isCoachAcross;
    private int isStudentAcross;
    private int responseTime;


    public T8900_0900_terminal_set() {
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


    @Field(index = 25, type = DataType.BYTE,desc = "参数编号")
    public int getParamNo() {
        return paramNo;
    }

    public void setParamNo(int paramNo) {
        this.paramNo = paramNo;
    }

    @Field(index = 26, type = DataType.BYTE,desc = "定时拍照时间间隔")
    public int getPhotoTime() {
        return photoTime;
    }

    public void setPhotoTime(int photoTime) {
        this.photoTime = photoTime;
    }

    @Field(index = 27, type = DataType.BYTE,desc = "照片上传设置")
    public int getUpSet() {
        return upSet;
    }

    public void setUpSet(int upSet) {
        this.upSet = upSet;
    }

    @Field(index = 28, type = DataType.BYTE,desc = "是否报读附加消息")
    public int getIsReadAdd() {
        return isReadAdd;
    }

    public void setIsReadAdd(int isReadAdd) {
        this.isReadAdd = isReadAdd;
    }

    @Field(index = 29, type = DataType.BYTE,desc = "熄火后停止学时计时的延时时间")
    public int getClassDelayTime() {
        return classDelayTime;
    }

    public void setClassDelayTime(int classDelayTime) {
        this.classDelayTime = classDelayTime;
    }

    @Field(index = 30, type = DataType.WORD,desc = "熄火后GNSS数据包上传间隔")
    public int getUpTime() {
        return upTime;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    @Field(index = 32, type = DataType.WORD,desc = "熄火后教练自动登出的延时时间")
    public int getCoachDelayTime() {
        return coachDelayTime;
    }

    public void setCoachDelayTime(int coachDelayTime) {
        this.coachDelayTime = coachDelayTime;
    }

    @Field(index = 34, type = DataType.WORD,desc = "重新验证身份时间")
    public int getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(int verifyTime) {
        this.verifyTime = verifyTime;
    }

    @Field(index = 36, type = DataType.BYTE,desc = "教练跨校教学")
    public int getIsCoachAcross() {
        return isCoachAcross;
    }

    public void setIsCoachAcross(int isCoachAcross) {
        this.isCoachAcross = isCoachAcross;
    }

    @Field(index = 37, type = DataType.BYTE,desc = "学员跨校学习")
    public int getIsStudentAcross() {
        return isStudentAcross;
    }

    public void setIsStudentAcross(int isStudentAcross) {
        this.isStudentAcross = isStudentAcross;
    }

    @Field(index = 38, type = DataType.WORD,desc = "响应平台同类消息时间间隔")
    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
}