package org.yzh.web.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class JsTrainLocation {
    private Integer id;

    private Integer type;

    private String devnum;

    private Integer markId;

    private LocalDateTime deviceTime;

    private String mobileNo;

    private String plateNo;

    private Integer warningMark;

    private Integer status;

    private Integer latitude;

    private Integer longitude;

    private Integer driveSpeed;

    private Integer starSpeed;

    private Integer engineSpeed;

    private Integer direction;

    private Integer mapFenceId;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDevnum() {
        return devnum;
    }

    public void setDevnum(String devnum) {
        this.devnum = devnum;
    }

    public Integer getMarkId() {
        return markId;
    }

    public void setMarkId(Integer markId) {
        this.markId = markId;
    }

    public LocalDateTime getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(LocalDateTime deviceTime) {
        this.deviceTime = deviceTime;
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

    public Integer getDriveSpeed() {
        return driveSpeed;
    }

    public void setDriveSpeed(Integer driveSpeed) {
        this.driveSpeed = driveSpeed;
    }

    public Integer getStarSpeed() {
        return starSpeed;
    }

    public void setStarSpeed(Integer starSpeed) {
        this.starSpeed = starSpeed;
    }

    public Integer getEngineSpeed() {
        return engineSpeed;
    }

    public void setEngineSpeed(Integer engineSpeed) {
        this.engineSpeed = engineSpeed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
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
        JsTrainLocation other = (JsTrainLocation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDevnum() == null ? other.getDevnum() == null : this.getDevnum().equals(other.getDevnum()))
            && (this.getMarkId() == null ? other.getMarkId() == null : this.getMarkId().equals(other.getMarkId()))
            && (this.getDeviceTime() == null ? other.getDeviceTime() == null : this.getDeviceTime().equals(other.getDeviceTime()))
            && (this.getMobileNo() == null ? other.getMobileNo() == null : this.getMobileNo().equals(other.getMobileNo()))
            && (this.getPlateNo() == null ? other.getPlateNo() == null : this.getPlateNo().equals(other.getPlateNo()))
            && (this.getWarningMark() == null ? other.getWarningMark() == null : this.getWarningMark().equals(other.getWarningMark()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getDriveSpeed() == null ? other.getDriveSpeed() == null : this.getDriveSpeed().equals(other.getDriveSpeed()))
            && (this.getStarSpeed() == null ? other.getStarSpeed() == null : this.getStarSpeed().equals(other.getStarSpeed()))
            && (this.getDirection() == null ? other.getDirection() == null : this.getDirection().equals(other.getDirection()))
            && (this.getMapFenceId() == null ? other.getMapFenceId() == null : this.getMapFenceId().equals(other.getMapFenceId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDevnum() == null) ? 0 : getDevnum().hashCode());
        result = prime * result + ((getMarkId() == null) ? 0 : getMarkId().hashCode());
        result = prime * result + ((getDeviceTime() == null) ? 0 : getDeviceTime().hashCode());
        result = prime * result + ((getMobileNo() == null) ? 0 : getMobileNo().hashCode());
        result = prime * result + ((getPlateNo() == null) ? 0 : getPlateNo().hashCode());
        result = prime * result + ((getWarningMark() == null) ? 0 : getWarningMark().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getDriveSpeed() == null) ? 0 : getDriveSpeed().hashCode());
        result = prime * result + ((getStarSpeed() == null) ? 0 : getStarSpeed().hashCode());
        result = prime * result + ((getDirection() == null) ? 0 : getDirection().hashCode());
        result = prime * result + ((getMapFenceId() == null) ? 0 : getMapFenceId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}