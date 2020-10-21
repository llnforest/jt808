package org.yzh.web.model.entity;

import org.yzh.web.commons.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocationDO {

    private String deviceId;
    private String mobileNo;
    private String plateNo;
    private Integer warningMark;
    private Integer status;
    private Integer latitude;
    private Integer longitude;
    private Integer altitude;
    private Integer speed;
    private Integer direction;
    private LocalDateTime deviceTime;
    private LocalDate deviceDate;
    private Integer mapFenceId;
    private LocalDateTime createTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Integer getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(Integer warningMark) {
        this.warningMark = warningMark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public LocalDateTime getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(LocalDateTime deviceTime) {
        this.deviceTime = deviceTime;
    }

    public LocalDate getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(LocalDate deviceDate) {
        this.deviceDate = deviceDate;
    }

    public Integer getMapFenceId() {
        return mapFenceId;
    }

    public void setMapFenceId(Integer mapFenceId) {
        this.mapFenceId = mapFenceId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LocationDO other = (LocationDO) that;
        return (this.getDeviceTime() == null ? other.getDeviceTime() == null : this.getDeviceTime().equals(other.getDeviceTime()))
                && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceTime() == null) ? 0 : getDeviceTime().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder(32)
                .append("[").append(deviceId).append(",")
                .append(DateUtils.yyMMddHHmmss.format(deviceTime)).append("]").toString();
    }
}