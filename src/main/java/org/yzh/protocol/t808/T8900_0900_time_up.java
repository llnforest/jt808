package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.time.LocalDateTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.数据上行透传, JT808.数据下行透传})
public class T8900_0900_time_up extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x0203;
    private int msgAttr;
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private String timeNo;
    private int upType;
    private String studentNo;
    private String coachNo;
    private String classId;
    private String addTime;
    private String classNum;
    private int status;
    private int speed;
    private int km;
    private T0200 t0200;

    public T8900_0900_time_up() {
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

    @Field(index = 25, type = DataType.BYTES,length = 26,desc = "学时记录编号")
    public String getTimeNo() {
        return timeNo;
    }

    public void setTimeNo(String timeNo) {
        this.timeNo = timeNo;
    }

    @Field(index = 51, type = DataType.BYTE,desc = "上报类型")
    public int getUpType() {
        return upType;
    }

    public void setUpType(int upType) {
        this.upType = upType;
    }

    @Field(index = 52, type = DataType.BYTES,length = 16,desc = "学员编号")
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }


    @Field(index = 68, type = DataType.BYTES,length = 16,desc = "教练编号")
    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    @Field(index = 84, type = DataType.DWORD,desc = "课堂ID")
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Field(index = 88, type = DataType.BCD8421,length = 3,desc = "记录产生时间")
    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Field(index = 91, type = DataType.BCD8421,length = 5,desc = "培训课程")
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    @Field(index = 96, type = DataType.BYTE,desc = "记录状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 97, type = DataType.WORD,desc = "最大速度")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 99, type = DataType.WORD,desc = "里程")
    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    @Field(index = 101, type = DataType.OBJ,length = 28, desc = "基本GNSS数据包")
    public T0200 getT0200() {
        return t0200;
    }

    public void setT0200(T0200 t0200) {
        this.t0200 = t0200;
    }

}