package org.yzh.web.model.entity;

import java.util.Date;

public class JsTerminalOperateParam {
    private Integer id;

    private String inscode;

    private String devnum;

    private String phone;

    private Byte paramNo;

    private Short photoTime;

    private Integer upSet;

    private Integer isReadAdd;

    private Short classDelayTime;

    private Integer upTime;

    private Short coachDelayTime;

    private Short verifyTime;

    private Integer isCoachAcross;

    private Integer isStudentAcross;

    private Short responseTime;

    private Integer downStatus;

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

    public String getDevnum() {
        return devnum;
    }

    public void setDevnum(String devnum) {
        this.devnum = devnum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getParamNo() {
        return paramNo;
    }

    public void setParamNo(Byte paramNo) {
        this.paramNo = paramNo;
    }

    public Short getPhotoTime() {
        return photoTime;
    }

    public void setPhotoTime(Short photoTime) {
        this.photoTime = photoTime;
    }

    public Integer getUpSet() {
        return upSet;
    }

    public void setUpSet(Integer upSet) {
        this.upSet = upSet;
    }

    public Integer getIsReadAdd() {
        return isReadAdd;
    }

    public void setIsReadAdd(Integer isReadAdd) {
        this.isReadAdd = isReadAdd;
    }

    public Short getClassDelayTime() {
        return classDelayTime;
    }

    public void setClassDelayTime(Short classDelayTime) {
        this.classDelayTime = classDelayTime;
    }

    public Integer getUpTime() {
        return upTime;
    }

    public void setUpTime(Integer upTime) {
        this.upTime = upTime;
    }

    public Short getCoachDelayTime() {
        return coachDelayTime;
    }

    public void setCoachDelayTime(Short coachDelayTime) {
        this.coachDelayTime = coachDelayTime;
    }

    public Short getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Short verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Integer getIsCoachAcross() {
        return isCoachAcross;
    }

    public void setIsCoachAcross(Integer isCoachAcross) {
        this.isCoachAcross = isCoachAcross;
    }

    public Integer getIsStudentAcross() {
        return isStudentAcross;
    }

    public void setIsStudentAcross(Integer isStudentAcross) {
        this.isStudentAcross = isStudentAcross;
    }

    public Short getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Short responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getDownStatus() {
        return downStatus;
    }

    public void setDownStatus(Integer downStatus) {
        this.downStatus = downStatus;
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
        JsTerminalOperateParam other = (JsTerminalOperateParam) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getDevnum() == null ? other.getDevnum() == null : this.getDevnum().equals(other.getDevnum()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getParamNo() == null ? other.getParamNo() == null : this.getParamNo().equals(other.getParamNo()))
            && (this.getPhotoTime() == null ? other.getPhotoTime() == null : this.getPhotoTime().equals(other.getPhotoTime()))
            && (this.getUpSet() == null ? other.getUpSet() == null : this.getUpSet().equals(other.getUpSet()))
            && (this.getIsReadAdd() == null ? other.getIsReadAdd() == null : this.getIsReadAdd().equals(other.getIsReadAdd()))
            && (this.getClassDelayTime() == null ? other.getClassDelayTime() == null : this.getClassDelayTime().equals(other.getClassDelayTime()))
            && (this.getUpTime() == null ? other.getUpTime() == null : this.getUpTime().equals(other.getUpTime()))
            && (this.getCoachDelayTime() == null ? other.getCoachDelayTime() == null : this.getCoachDelayTime().equals(other.getCoachDelayTime()))
            && (this.getVerifyTime() == null ? other.getVerifyTime() == null : this.getVerifyTime().equals(other.getVerifyTime()))
            && (this.getIsCoachAcross() == null ? other.getIsCoachAcross() == null : this.getIsCoachAcross().equals(other.getIsCoachAcross()))
            && (this.getIsStudentAcross() == null ? other.getIsStudentAcross() == null : this.getIsStudentAcross().equals(other.getIsStudentAcross()))
            && (this.getResponseTime() == null ? other.getResponseTime() == null : this.getResponseTime().equals(other.getResponseTime()))
            && (this.getDownStatus() == null ? other.getDownStatus() == null : this.getDownStatus().equals(other.getDownStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInscode() == null) ? 0 : getInscode().hashCode());
        result = prime * result + ((getDevnum() == null) ? 0 : getDevnum().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getParamNo() == null) ? 0 : getParamNo().hashCode());
        result = prime * result + ((getPhotoTime() == null) ? 0 : getPhotoTime().hashCode());
        result = prime * result + ((getUpSet() == null) ? 0 : getUpSet().hashCode());
        result = prime * result + ((getIsReadAdd() == null) ? 0 : getIsReadAdd().hashCode());
        result = prime * result + ((getClassDelayTime() == null) ? 0 : getClassDelayTime().hashCode());
        result = prime * result + ((getUpTime() == null) ? 0 : getUpTime().hashCode());
        result = prime * result + ((getCoachDelayTime() == null) ? 0 : getCoachDelayTime().hashCode());
        result = prime * result + ((getVerifyTime() == null) ? 0 : getVerifyTime().hashCode());
        result = prime * result + ((getIsCoachAcross() == null) ? 0 : getIsCoachAcross().hashCode());
        result = prime * result + ((getIsStudentAcross() == null) ? 0 : getIsStudentAcross().hashCode());
        result = prime * result + ((getResponseTime() == null) ? 0 : getResponseTime().hashCode());
        result = prime * result + ((getDownStatus() == null) ? 0 : getDownStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}