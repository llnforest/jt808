package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class T8900_0900_coach_up {

    private String coachNo;
    private String coachIdentity;
    private String coachType;
    private T0200 t0200;

    public T8900_0900_coach_up() {
    }


    @Field(index = 0, type = DataType.BYTES,length = 16,desc = "教练员编号")
    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    @Field(index = 16, type = DataType.BYTES,length = 18, desc = "教练员身份证号")
    public String getCoachIdentity() {
        return coachIdentity;
    }

    public void setCoachIdentity(String coachIdentity) {
        this.coachIdentity = coachIdentity;
    }

    @Field(index = 34, type = DataType.BYTES,length = 2, desc = "准教车型")
    public String getCoachType() {
        return coachType;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType;
    }


    @Field(index = 36, type = DataType.OBJ,length = 28, desc = "基本GNSS数据包")
    public T0200 getT0200() {
        return t0200;
    }

    public void setT0200(T0200 t0200) {
        this.t0200 = t0200;
    }
}