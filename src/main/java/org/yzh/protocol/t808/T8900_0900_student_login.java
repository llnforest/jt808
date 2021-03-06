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
@Message({JT808.上报学员登录})
public class T8900_0900_student_login extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x0201;
    private int msgAttr = 2;//2需要应答(不加密) 0不需要应答(不加密) 10需要应答(SHA256加密)  8不需要应答(SHA256加密)
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private String studentNo;
    private String coachNo;

    private String className;

    private int classId;

    private T0200 t0200;

    public T8900_0900_student_login() {
    }

    public T8900_0900_student_login(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.数据上行透传.substring(2),16), serialNo, mobileNo));
    }

    public T8900_0900_student_login(int serialNo, String mobileNo) {
        super(new Header(Integer.parseInt(JT808.数据上行透传.substring(2),16), serialNo, mobileNo));
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


    @Field(index = 27, type = DataType.BYTES,length = 16, desc = "学员编号")
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
    @Field(index = 43, type = DataType.BYTES,length = 16,desc = "教练员编号")
    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    @Field(index = 59, type = DataType.BCD8421,length = 5, desc = "培训课程")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Field(index = 64, type = DataType.DWORD,desc = "课堂ID")
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }


    @Field(index = 68, type = DataType.OBJ,length = 28, desc = "基本GNSS数据包")
    public T0200 getT0200() {
        return t0200;
    }

    public void setT0200(T0200 t0200) {
        this.t0200 = t0200;
    }






}