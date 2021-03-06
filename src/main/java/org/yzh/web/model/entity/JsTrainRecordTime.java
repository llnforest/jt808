package org.yzh.web.model.entity;

import java.util.Date;

public class JsTrainRecordTime {
    private Integer id;

    private String rnum;

    private String stunum;

    private String coachnum;

    private String classId;



    private String subjcode;

    private Integer status;

    private Integer upType;

    private String maxspeed;

    private String mileage;

    private Integer syncStatus;

    private String reason;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getStunum() {
        return stunum;
    }

    public void setStunum(String stunum) {
        this.stunum = stunum;
    }

    public String getCoachnum() {
        return coachnum;
    }

    public void setCoachnum(String coachnum) {
        this.coachnum = coachnum;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubjcode() {
        return subjcode;
    }

    public void setSubjcode(String subjcode) {
        this.subjcode = subjcode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUpType() {
        return upType;
    }

    public void setUpType(Integer upType) {
        this.upType = upType;
    }

    public String getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(String maxspeed) {
        this.maxspeed = maxspeed;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        JsTrainRecordTime other = (JsTrainRecordTime) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRnum() == null ? other.getRnum() == null : this.getRnum().equals(other.getRnum()))
            && (this.getStunum() == null ? other.getStunum() == null : this.getStunum().equals(other.getStunum()))
            && (this.getCoachnum() == null ? other.getCoachnum() == null : this.getCoachnum().equals(other.getCoachnum()))
            && (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))

            && (this.getSubjcode() == null ? other.getSubjcode() == null : this.getSubjcode().equals(other.getSubjcode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getUpType() == null ? other.getUpType() == null : this.getUpType().equals(other.getUpType()))
            && (this.getMaxspeed() == null ? other.getMaxspeed() == null : this.getMaxspeed().equals(other.getMaxspeed()))
            && (this.getMileage() == null ? other.getMileage() == null : this.getMileage().equals(other.getMileage()))
            && (this.getSyncStatus() == null ? other.getSyncStatus() == null : this.getSyncStatus().equals(other.getSyncStatus()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRnum() == null) ? 0 : getRnum().hashCode());
        result = prime * result + ((getStunum() == null) ? 0 : getStunum().hashCode());
        result = prime * result + ((getCoachnum() == null) ? 0 : getCoachnum().hashCode());
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getSubjcode() == null) ? 0 : getSubjcode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUpType() == null) ? 0 : getUpType().hashCode());
        result = prime * result + ((getMaxspeed() == null) ? 0 : getMaxspeed().hashCode());
        result = prime * result + ((getMileage() == null) ? 0 : getMileage().hashCode());
        result = prime * result + ((getSyncStatus() == null) ? 0 : getSyncStatus().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}