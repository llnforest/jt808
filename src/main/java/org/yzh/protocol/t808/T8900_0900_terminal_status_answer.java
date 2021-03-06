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
@Message({JT808.设置禁训状态应答})
public class T8900_0900_terminal_status_answer extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x0502;
    private int msgAttr = 0;//2需要应答(不加密) 0不需要应答(不加密) 10需要应答(SHA256加密)  8不需要应答(SHA256加密)
    private int packetNo;
    private String terminalNo;
    private int dataLength = 3;

    private int result;
    private int status;
    private int msgLength;
    private String msgContent;


    public T8900_0900_terminal_status_answer() {
    }

    public T8900_0900_terminal_status_answer(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.数据上行透传.substring(2),16), serialNo, mobileNo));
    }

    public T8900_0900_terminal_status_answer(int serialNo, String mobileNo) {
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


    @Field(index = 27, type = DataType.BYTE,desc = "执行结果")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Field(index = 28, type = DataType.BYTE,desc = "禁训状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 29, type = DataType.BYTE,desc = "提示消息长度")
    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    @Field(index = 30, type = DataType.STRING,desc = "提示消息内容")
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
        try{
            this.msgLength = msgContent.getBytes("GBK").length;
            this.dataLength += this.msgLength;
        }catch (Exception ex){
            this.msgLength = 0;
        }
    }
}