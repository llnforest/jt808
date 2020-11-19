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
public class T8900_0900_coach_login extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId;
    private int msgAttr;
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private String coachNo;
    private String coachIdentity;
    private String coachType;

    public T8900_0900_coach_login() {
    }


    @Field(index = 0, type = DataType.BYTE, desc = "透传消息类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 2, type = DataType.WORD, desc = "透传消息Id")
    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    @Field(index = 4, type = DataType.WORD, desc = "扩展消息属性")
    public int getMsgAttr() {
        return msgAttr;
    }

    public void setMsgAttr(int msgAttr) {
        this.msgAttr = msgAttr;
    }

    @Field(index = 6, type = DataType.WORD, desc = "驾培包序号")
    public int getPacketNo() {
        return packetNo;
    }

    public void setPacketNo(int packetNo) {
        this.packetNo = packetNo;
    }

    @Field(index = 8, type = DataType.STRING,length = 16,desc = "终端编号")
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    @Field(index = 24, type = DataType.WORD, desc = "数据内容长度")
    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    @Field(index = 26, type = DataType.STRING,length = 5,desc = "教练员编号")
    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    @Field(index = 31, type = DataType.STRING,length = 18, desc = "教练员身份证号")
    public String getCoachIdentity() {
        return coachIdentity;
    }

    public void setCoachIdentity(String coachIdentity) {
        this.coachIdentity = coachIdentity;
    }

    @Field(index = 49, type = DataType.STRING,length = 2, desc = "准教车型")
    public String getCoachType() {
        return coachType;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType;
    }

}