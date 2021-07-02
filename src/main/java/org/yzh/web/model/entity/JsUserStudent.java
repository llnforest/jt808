package org.yzh.web.model.entity;

import java.util.Date;

public class JsUserStudent {
    private Integer id;

    private String stunum;

    private String inscode;

    private String cardtype;

    private String idcard;

    private String nationality;

    private String name;

    private String sex;

    private String phone;

    private String address;

    private String photo;

    private String fingerprint;

    private String busitype;

    private String drilicnum;

    private String fstdrilicdate;

    private String perdritype;

    private String traintype;

    private String applydate;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    private Integer totalStudyTime;

    private Integer nowStudyTime;

    private Integer totalStudyKm;

    private Integer nowStudyKm;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStunum() {
        return stunum;
    }

    public void setStunum(String stunum) {
        this.stunum = stunum;
    }

    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getBusitype() {
        return busitype;
    }

    public void setBusitype(String busitype) {
        this.busitype = busitype;
    }

    public String getDrilicnum() {
        return drilicnum;
    }

    public void setDrilicnum(String drilicnum) {
        this.drilicnum = drilicnum;
    }

    public String getFstdrilicdate() {
        return fstdrilicdate;
    }

    public void setFstdrilicdate(String fstdrilicdate) {
        this.fstdrilicdate = fstdrilicdate;
    }

    public String getPerdritype() {
        return perdritype;
    }

    public void setPerdritype(String perdritype) {
        this.perdritype = perdritype;
    }

    public String getTraintype() {
        return traintype;
    }

    public void setTraintype(String traintype) {
        this.traintype = traintype;
    }

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate;
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

    public Integer getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(Integer totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }

    public Integer getNowStudyTime() {
        return nowStudyTime;
    }

    public void setNowStudyTime(Integer nowStudyTime) {
        this.nowStudyTime = nowStudyTime;
    }

    public Integer getTotalStudyKm() {
        return totalStudyKm;
    }

    public void setTotalStudyKm(Integer totalStudyKm) {
        this.totalStudyKm = totalStudyKm;
    }

    public Integer getNowStudyKm() {
        return nowStudyKm;
    }

    public void setNowStudyKm(Integer nowStudyKm) {
        this.nowStudyKm = nowStudyKm;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        JsUserStudent other = (JsUserStudent) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStunum() == null ? other.getStunum() == null : this.getStunum().equals(other.getStunum()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getCardtype() == null ? other.getCardtype() == null : this.getCardtype().equals(other.getCardtype()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getNationality() == null ? other.getNationality() == null : this.getNationality().equals(other.getNationality()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getFingerprint() == null ? other.getFingerprint() == null : this.getFingerprint().equals(other.getFingerprint()))
            && (this.getBusitype() == null ? other.getBusitype() == null : this.getBusitype().equals(other.getBusitype()))
            && (this.getDrilicnum() == null ? other.getDrilicnum() == null : this.getDrilicnum().equals(other.getDrilicnum()))
            && (this.getFstdrilicdate() == null ? other.getFstdrilicdate() == null : this.getFstdrilicdate().equals(other.getFstdrilicdate()))
            && (this.getPerdritype() == null ? other.getPerdritype() == null : this.getPerdritype().equals(other.getPerdritype()))
            && (this.getTraintype() == null ? other.getTraintype() == null : this.getTraintype().equals(other.getTraintype()))
            && (this.getApplydate() == null ? other.getApplydate() == null : this.getApplydate().equals(other.getApplydate()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getTotalStudyTime() == null ? other.getTotalStudyTime() == null : this.getTotalStudyTime().equals(other.getTotalStudyTime()))
            && (this.getNowStudyTime() == null ? other.getNowStudyTime() == null : this.getNowStudyTime().equals(other.getNowStudyTime()))
            && (this.getTotalStudyKm() == null ? other.getTotalStudyKm() == null : this.getTotalStudyKm().equals(other.getTotalStudyKm()))
            && (this.getNowStudyKm() == null ? other.getNowStudyKm() == null : this.getNowStudyKm().equals(other.getNowStudyKm()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStunum() == null) ? 0 : getStunum().hashCode());
        result = prime * result + ((getInscode() == null) ? 0 : getInscode().hashCode());
        result = prime * result + ((getCardtype() == null) ? 0 : getCardtype().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getNationality() == null) ? 0 : getNationality().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getFingerprint() == null) ? 0 : getFingerprint().hashCode());
        result = prime * result + ((getBusitype() == null) ? 0 : getBusitype().hashCode());
        result = prime * result + ((getDrilicnum() == null) ? 0 : getDrilicnum().hashCode());
        result = prime * result + ((getFstdrilicdate() == null) ? 0 : getFstdrilicdate().hashCode());
        result = prime * result + ((getPerdritype() == null) ? 0 : getPerdritype().hashCode());
        result = prime * result + ((getTraintype() == null) ? 0 : getTraintype().hashCode());
        result = prime * result + ((getApplydate() == null) ? 0 : getApplydate().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getTotalStudyTime() == null) ? 0 : getTotalStudyTime().hashCode());
        result = prime * result + ((getNowStudyTime() == null) ? 0 : getNowStudyTime().hashCode());
        result = prime * result + ((getTotalStudyKm() == null) ? 0 : getTotalStudyKm().hashCode());
        result = prime * result + ((getNowStudyKm() == null) ? 0 : getNowStudyKm().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}