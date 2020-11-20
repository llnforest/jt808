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
public class T8900_0900_terminal_set extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x8501;
    private int msgAttr;
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private int paramNo;
    private int crondMin;
    private int upSet;
    private int isReadAdd;
    private int stopMin;
    private int gnssSec;
    private int coachMin;
    private int resetMin;
    private int isAllowCoashAccrossSchool;
    private int isAllowStudentAccrossSchool;
    private int responseSec;


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
    public int getCrondMin() {
        return crondMin;
    }

    public void setCrondMin(int crondMin) {
        this.crondMin = crondMin;
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
    public int getStopMin() {
        return stopMin;
    }

    public void setStopMin(int stopMin) {
        this.stopMin = stopMin;
    }

    @Field(index = 30, type = DataType.WORD,desc = "熄火后GNSS数据包上传间隔")
    public int getGnssSec() {
        return gnssSec;
    }

    public void setGnssSec(int gnssSec) {
        this.gnssSec = gnssSec;
    }

    @Field(index = 32, type = DataType.WORD,desc = "熄火后教练自动登出的延时时间")
    public int getCoachMin() {
        return coachMin;
    }

    public void setCoachMin(int coachMin) {
        this.coachMin = coachMin;
    }

    @Field(index = 34, type = DataType.WORD,desc = "重新验证身份时间")
    public int getResetMin() {
        return resetMin;
    }

    public void setResetMin(int resetMin) {
        this.resetMin = resetMin;
    }

    @Field(index = 36, type = DataType.BYTE,desc = "教练跨校教学")
    public int getIsAllowCoashAccrossSchool() {
        return isAllowCoashAccrossSchool;
    }

    public void setIsAllowCoashAccrossSchool(int isAllowCoashAccrossSchool) {
        this.isAllowCoashAccrossSchool = isAllowCoashAccrossSchool;
    }

    @Field(index = 37, type = DataType.BYTE,desc = "学员跨校学习")
    public int getIsAllowStudentAccrossSchool() {
        return isAllowStudentAccrossSchool;
    }

    public void setIsAllowStudentAccrossSchool(int isAllowStudentAccrossSchool) {
        this.isAllowStudentAccrossSchool = isAllowStudentAccrossSchool;
    }

    @Field(index = 38, type = DataType.WORD,desc = "响应平台同类消息时间间隔")
    public int getResponseSec() {
        return responseSec;
    }

    public void setResponseSec(int responseSec) {
        this.responseSec = responseSec;
    }
}