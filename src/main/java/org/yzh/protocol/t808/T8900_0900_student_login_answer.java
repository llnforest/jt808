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
@Message({JT808.学员登录应答})
public class T8900_0900_student_login_answer extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x8201;
    private int msgAttr = 0;//2需要应答(不加密) 0不需要应答(不加密) 10需要应答(SHA256加密)  8不需要应答(SHA256加密)
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private int loginResult;
    private String studentNo;
    private int totalClassNum;
    private int completedClassNum;
    private int totalKm;
    private int completedKm;

    private int isRead;
    private int addLength;
    private String addMsg;


    public T8900_0900_student_login_answer() {
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

    @Field(index = 25, type = DataType.BYTE,desc = "登录结果")
    public int getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(int loginResult) {
        this.loginResult = loginResult;
    }

    @Field(index = 26, type = DataType.BYTES,length = 16,desc = "学员编号")
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    @Field(index = 42, type = DataType.WORD,desc = "总培训学时")
    public int getTotalClassNum() {
        return totalClassNum;
    }

    public void setTotalClassNum(int totalClassNum) {
        this.totalClassNum = totalClassNum;
    }

    @Field(index = 44, type = DataType.WORD,desc = "当前培训部分已完成学时")
    public int getCompletedClassNum() {
        return completedClassNum;
    }

    public void setCompletedClassNum(int completedClassNum) {
        this.completedClassNum = completedClassNum;
    }

    @Field(index = 46, type = DataType.WORD,desc = "总培训里程")
    public int getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(int totalKm) {
        this.totalKm = totalKm;
    }

    @Field(index = 48, type = DataType.WORD,desc = "当前培训部分已完成里程")
    public int getCompletedKm() {
        return completedKm;
    }

    public void setCompletedKm(int completedKm) {
        this.completedKm = completedKm;
    }

    @Field(index = 50, type = DataType.BYTE,desc = "是否报读附加消息")
    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Field(index = 51, type = DataType.BYTE,desc = "附加消息长度")
    public int getAddLength() {
        return addLength;
    }

    public void setAddLength(int addLength) {
        this.addLength = addLength;
    }

    @Field(index = 52, type = DataType.STRING,desc = "附加消息")
    public String getAddMsg() {
        return addMsg;
    }

    public void setAddMsg(String addMsg) {
        this.addMsg = addMsg;
    }




}