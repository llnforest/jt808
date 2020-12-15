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
@Message(JT808.终端注册应答)
public class T8100 extends AbstractMessage<Header> {

    /** 0：成功 */
    public static final int Success = 0;
    /** 1：车辆已被注册 */
    public static final int AlreadyRegisteredVehicle = 1;
    /** 2：数据库中无该车辆 */
    public static final int NotFoundVehicle = 2;
    /** 3：终端已被注册 */
    public static final int AlreadyRegisteredTerminal = 3;
    /** 4：数据库中无该终端 */
    public static final int NotFoundTerminal = 4;

    private int serialNo;
    private int resultCode;
    private String platNum;
    private String inscode;
    private String devnum;
    private String certSign;
    private String cert;

    public T8100() {
    }

    public T8100(int serialNo, String mobileNo) {
        super(new Header(Integer.parseInt(JT808.终端注册应答.substring(2),16), serialNo, mobileNo));
    }

    /** 对应的终端注册消息的流水号 */
    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    /** 0-4 */
    @Field(index = 2, type = DataType.BYTE, desc = "结果")
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @Field(index = 3, type = DataType.BYTE, desc = "平台编号")
    public String getPlatNum() {
        return platNum;
    }

    public void setPlatNum(String platNum) {
        this.platNum = platNum;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "培训机构编号")
    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
    }

    @Field(index = 24, type = DataType.BYTE, desc = "计时终端编号")
    public String getDevnum() {
        return devnum;
    }

    public void setDevnum(String devnum) {
        this.devnum = devnum;
    }

    @Field(index = 40, type = DataType.BYTE, desc = "证书口令")
    public String getCertSign() {
        return certSign;
    }

    public void setCertSign(String certSign) {
        this.certSign = certSign;
    }

    @Field(index = 52, type = DataType.BYTE, desc = "终端证书")
    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
}