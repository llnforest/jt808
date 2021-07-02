package org.yzh.web.model.entity;

import java.util.Date;

public class JsTrainRecord {
    private Integer id;

    private String inscode;

    private String classId;

    private String stunum;

    private String coachnum;

    private String carnum;

    private String devnum;

    private String platnum;

    private String recnum;

    private Integer classCode;

    private String trainCode;

    private Integer partCode;

    private String projectCode;

    private String subjcode;

    private String starttime;

    private String endtime;

    private String duration;

    private String mileage;

    private String avevelocity;

    private Integer status;

    private Integer reportStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getDevnum() {
        return devnum;
    }

    public void setDevnum(String devnum) {
        this.devnum = devnum;
    }

    public String getPlatnum() {
        return platnum;
    }

    public void setPlatnum(String platnum) {
        this.platnum = platnum;
    }

    public String getRecnum() {
        return recnum;
    }

    public void setRecnum(String recnum) {
        this.recnum = recnum;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public Integer getPartCode() {
        return partCode;
    }

    public void setPartCode(Integer partCode) {
        this.partCode = partCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getSubjcode() {
        return subjcode;
    }

    public void setSubjcode(String subjcode) {
        this.subjcode = subjcode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getAvevelocity() {
        return avevelocity;
    }

    public void setAvevelocity(String avevelocity) {
        this.avevelocity = avevelocity;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        JsTrainRecord other = (JsTrainRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getStunum() == null ? other.getStunum() == null : this.getStunum().equals(other.getStunum()))
            && (this.getCoachnum() == null ? other.getCoachnum() == null : this.getCoachnum().equals(other.getCoachnum()))
            && (this.getCarnum() == null ? other.getCarnum() == null : this.getCarnum().equals(other.getCarnum()))
            && (this.getDevnum() == null ? other.getDevnum() == null : this.getDevnum().equals(other.getDevnum()))
            && (this.getPlatnum() == null ? other.getPlatnum() == null : this.getPlatnum().equals(other.getPlatnum()))
            && (this.getRecnum() == null ? other.getRecnum() == null : this.getRecnum().equals(other.getRecnum()))
            && (this.getClassCode() == null ? other.getClassCode() == null : this.getClassCode().equals(other.getClassCode()))
            && (this.getTrainCode() == null ? other.getTrainCode() == null : this.getTrainCode().equals(other.getTrainCode()))
            && (this.getPartCode() == null ? other.getPartCode() == null : this.getPartCode().equals(other.getPartCode()))
            && (this.getProjectCode() == null ? other.getProjectCode() == null : this.getProjectCode().equals(other.getProjectCode()))
            && (this.getSubjcode() == null ? other.getSubjcode() == null : this.getSubjcode().equals(other.getSubjcode()))
            && (this.getStarttime() == null ? other.getStarttime() == null : this.getStarttime().equals(other.getStarttime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getMileage() == null ? other.getMileage() == null : this.getMileage().equals(other.getMileage()))
            && (this.getAvevelocity() == null ? other.getAvevelocity() == null : this.getAvevelocity().equals(other.getAvevelocity()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInscode() == null) ? 0 : getInscode().hashCode());
        result = prime * result + ((getStunum() == null) ? 0 : getStunum().hashCode());
        result = prime * result + ((getCoachnum() == null) ? 0 : getCoachnum().hashCode());
        result = prime * result + ((getCarnum() == null) ? 0 : getCarnum().hashCode());
        result = prime * result + ((getDevnum() == null) ? 0 : getDevnum().hashCode());
        result = prime * result + ((getPlatnum() == null) ? 0 : getPlatnum().hashCode());
        result = prime * result + ((getRecnum() == null) ? 0 : getRecnum().hashCode());
        result = prime * result + ((getClassCode() == null) ? 0 : getClassCode().hashCode());
        result = prime * result + ((getTrainCode() == null) ? 0 : getTrainCode().hashCode());
        result = prime * result + ((getPartCode() == null) ? 0 : getPartCode().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getSubjcode() == null) ? 0 : getSubjcode().hashCode());
        result = prime * result + ((getStarttime() == null) ? 0 : getStarttime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getMileage() == null) ? 0 : getMileage().hashCode());
        result = prime * result + ((getAvevelocity() == null) ? 0 : getAvevelocity().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}