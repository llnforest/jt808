package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.Attribute;
import org.yzh.protocol.commons.transform.PositionAttributeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.位置信息查询应答, JT808.车辆控制应答})
public class T0201_0500 extends AbstractMessage<Header> {

    private int warningMark;
    private int status;
    private int latitude;
    private int longitude;
    private int driveSpeed;
    private int starSpeed;
    private int direction;
    private LocalDateTime dateTime;

    @Field(index = 0, type = DataType.DWORD, desc = "报警标志")
    public int getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(int warningMark) {
        this.warningMark = warningMark;
    }

    @Field(index = 4, type = DataType.DWORD, desc = "状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 8, type = DataType.DWORD, desc = "纬度")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Field(index = 12, type = DataType.DWORD, desc = "经度")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Field(index = 16, type = DataType.WORD, desc = "行驶记录速度")
    public int getDriveSpeed() {
        return driveSpeed;
    }

    public void setDriveSpeed(int driveSpeed) {
        this.driveSpeed = driveSpeed;
    }

    @Field(index = 18, type = DataType.WORD, desc = "卫星定位速度")
    public int getStarSpeed() {
        return starSpeed;
    }

    public void setStarSpeed(int starSpeed) {
        this.starSpeed = starSpeed;
    }

    @Field(index = 20, type = DataType.WORD, desc = "方向")
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Field(index = 21, type = DataType.BCD8421, length = 6, desc = "时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}