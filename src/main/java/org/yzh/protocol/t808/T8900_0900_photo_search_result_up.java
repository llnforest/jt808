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
@Message({JT808.上报照片查询结果})
public class T8900_0900_photo_search_result_up extends AbstractMessage<Header> {



    private int type = 0x13;
    private int msgId = 0x0303;
    private int msgAttr = 2;//2需要应答(不加密) 0不需要应答(不加密) 10需要应答(SHA256加密)  8不需要应答(SHA256加密)
    private int packetNo;
    private int dataLength;
    private String terminalNo;

    private int isUp;
    private int needNum;
    private int totalNum;
    private String photoNum1;
    private String photoNum2;
    private String photoNum3;


    public T8900_0900_photo_search_result_up() {
    }

    public T8900_0900_photo_search_result_up(String mobileNo, int serialNo) {
        super(new Header(Integer.parseInt(JT808.数据上行透传.substring(2),16), serialNo, mobileNo));
    }

    public T8900_0900_photo_search_result_up(int serialNo, String mobileNo) {
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


    @Field(index = 27, type = DataType.BYTE,desc = "是否上报结束")
    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }

    @Field(index = 28, type = DataType.BYTE,desc = "符合条件的照片总数")
    public int getNeedNum() {
        return needNum;
    }

    public void setNeedNum(int needNum) {
        this.needNum = needNum;
    }

    @Field(index = 29, type = DataType.BYTE,desc = "此次发送的照片数目")
    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Field(index = 30, type = DataType.BYTES,length = 10,desc = "照片编号1")
    public String getPhotoNum1() {
        return photoNum1;
    }

    public void setPhotoNum1(String photoNum1) {
        this.photoNum1 = photoNum1;
    }

    @Field(index = 40, type = DataType.BYTES,length = 10,desc = "照片编号1")
    public String getPhotoNum2() {
        return photoNum2;
    }

    public void setPhotoNum2(String photoNum2) {
        this.photoNum2 = photoNum2;
    }

    @Field(index = 50, type = DataType.BYTES,length = 10,desc = "照片编号1")
    public String getPhotoNum3() {
        return photoNum3;
    }

    public void setPhotoNum3(String photoNum3) {
        this.photoNum3 = photoNum3;
    }
}