package org.yzh.web.model.entity;

import java.util.Date;

public class JsUserCoach {
    private Integer id;

    private String coachnum;

    private String inscode;

    private String name;

    private String sex;

    private String idcard;

    private String mobile;

    private String address;

    private String photo;

    private String fingerprint;

    private String drilicence;

    private String fstdrilicdate;

    private String occupationno;

    private String occupationlevel;

    private String dripermitted;

    private String teachpermitted;

    private String employstatus;

    private String hiredate;

    private String leavedate;

    private Integer sort;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoachnum() {
        return coachnum;
    }

    public void setCoachnum(String coachnum) {
        this.coachnum = coachnum;
    }

    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getDrilicence() {
        return drilicence;
    }

    public void setDrilicence(String drilicence) {
        this.drilicence = drilicence;
    }

    public String getFstdrilicdate() {
        return fstdrilicdate;
    }

    public void setFstdrilicdate(String fstdrilicdate) {
        this.fstdrilicdate = fstdrilicdate;
    }

    public String getOccupationno() {
        return occupationno;
    }

    public void setOccupationno(String occupationno) {
        this.occupationno = occupationno;
    }

    public String getOccupationlevel() {
        return occupationlevel;
    }

    public void setOccupationlevel(String occupationlevel) {
        this.occupationlevel = occupationlevel;
    }

    public String getDripermitted() {
        return dripermitted;
    }

    public void setDripermitted(String dripermitted) {
        this.dripermitted = dripermitted;
    }

    public String getTeachpermitted() {
        return teachpermitted;
    }

    public void setTeachpermitted(String teachpermitted) {
        this.teachpermitted = teachpermitted;
    }

    public String getEmploystatus() {
        return employstatus;
    }

    public void setEmploystatus(String employstatus) {
        this.employstatus = employstatus;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(String leavedate) {
        this.leavedate = leavedate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        JsUserCoach other = (JsUserCoach) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCoachnum() == null ? other.getCoachnum() == null : this.getCoachnum().equals(other.getCoachnum()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getFingerprint() == null ? other.getFingerprint() == null : this.getFingerprint().equals(other.getFingerprint()))
            && (this.getDrilicence() == null ? other.getDrilicence() == null : this.getDrilicence().equals(other.getDrilicence()))
            && (this.getFstdrilicdate() == null ? other.getFstdrilicdate() == null : this.getFstdrilicdate().equals(other.getFstdrilicdate()))
            && (this.getOccupationno() == null ? other.getOccupationno() == null : this.getOccupationno().equals(other.getOccupationno()))
            && (this.getOccupationlevel() == null ? other.getOccupationlevel() == null : this.getOccupationlevel().equals(other.getOccupationlevel()))
            && (this.getDripermitted() == null ? other.getDripermitted() == null : this.getDripermitted().equals(other.getDripermitted()))
            && (this.getTeachpermitted() == null ? other.getTeachpermitted() == null : this.getTeachpermitted().equals(other.getTeachpermitted()))
            && (this.getEmploystatus() == null ? other.getEmploystatus() == null : this.getEmploystatus().equals(other.getEmploystatus()))
            && (this.getHiredate() == null ? other.getHiredate() == null : this.getHiredate().equals(other.getHiredate()))
            && (this.getLeavedate() == null ? other.getLeavedate() == null : this.getLeavedate().equals(other.getLeavedate()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCoachnum() == null) ? 0 : getCoachnum().hashCode());
        result = prime * result + ((getInscode() == null) ? 0 : getInscode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getFingerprint() == null) ? 0 : getFingerprint().hashCode());
        result = prime * result + ((getDrilicence() == null) ? 0 : getDrilicence().hashCode());
        result = prime * result + ((getFstdrilicdate() == null) ? 0 : getFstdrilicdate().hashCode());
        result = prime * result + ((getOccupationno() == null) ? 0 : getOccupationno().hashCode());
        result = prime * result + ((getOccupationlevel() == null) ? 0 : getOccupationlevel().hashCode());
        result = prime * result + ((getDripermitted() == null) ? 0 : getDripermitted().hashCode());
        result = prime * result + ((getTeachpermitted() == null) ? 0 : getTeachpermitted().hashCode());
        result = prime * result + ((getEmploystatus() == null) ? 0 : getEmploystatus().hashCode());
        result = prime * result + ((getHiredate() == null) ? 0 : getHiredate().hashCode());
        result = prime * result + ((getLeavedate() == null) ? 0 : getLeavedate().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}