package org.yzh.web.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class JsTrainImg {
    private Integer id;

    private String classId;

    private String inscode;

    private String stunum;

    private String coachnum;

    private String subjcode;

    private String devnum;

    private String recnum;

    private String photonum;

    private LocalDateTime ptime;

    private String fileid;

    private Integer uptype;

    private Integer upmode;

    private Integer syncStatus;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
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

    public String getSubjcode() {
        return subjcode;
    }

    public void setSubjcode(String subjcode) {
        this.subjcode = subjcode;
    }

    public String getDevnum() {
        return devnum;
    }

    public void setDevnum(String devnum) {
        this.devnum = devnum;
    }

    public String getRecnum() {
        return recnum;
    }

    public void setRecnum(String recnum) {
        this.recnum = recnum;
    }

    public String getPhotonum() {
        return photonum;
    }

    public void setPhotonum(String photonum) {
        this.photonum = photonum;
    }

    public LocalDateTime getPtime() {
        return ptime;
    }

    public void setPtime(LocalDateTime ptime) {
        this.ptime = ptime;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public Integer getUptype() {
        return uptype;
    }

    public void setUptype(Integer uptype) {
        this.uptype = uptype;
    }

    public Integer getUpmode() {
        return upmode;
    }

    public void setUpmode(Integer upmode) {
        this.upmode = upmode;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
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
        JsTrainImg other = (JsTrainImg) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getStunum() == null ? other.getStunum() == null : this.getStunum().equals(other.getStunum()))
            && (this.getCoachnum() == null ? other.getCoachnum() == null : this.getCoachnum().equals(other.getCoachnum()))
            && (this.getSubjcode() == null ? other.getSubjcode() == null : this.getSubjcode().equals(other.getSubjcode()))
            && (this.getDevnum() == null ? other.getDevnum() == null : this.getDevnum().equals(other.getDevnum()))
            && (this.getRecnum() == null ? other.getRecnum() == null : this.getRecnum().equals(other.getRecnum()))
            && (this.getPhotonum() == null ? other.getPhotonum() == null : this.getPhotonum().equals(other.getPhotonum()))
            && (this.getPtime() == null ? other.getPtime() == null : this.getPtime().equals(other.getPtime()))
            && (this.getFileid() == null ? other.getFileid() == null : this.getFileid().equals(other.getFileid()))
            && (this.getUptype() == null ? other.getUptype() == null : this.getUptype().equals(other.getUptype()))
            && (this.getUpmode() == null ? other.getUpmode() == null : this.getUpmode().equals(other.getUpmode()))
            && (this.getSyncStatus() == null ? other.getSyncStatus() == null : this.getSyncStatus().equals(other.getSyncStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getInscode() == null) ? 0 : getInscode().hashCode());
        result = prime * result + ((getStunum() == null) ? 0 : getStunum().hashCode());
        result = prime * result + ((getCoachnum() == null) ? 0 : getCoachnum().hashCode());
        result = prime * result + ((getSubjcode() == null) ? 0 : getSubjcode().hashCode());
        result = prime * result + ((getDevnum() == null) ? 0 : getDevnum().hashCode());
        result = prime * result + ((getRecnum() == null) ? 0 : getRecnum().hashCode());
        result = prime * result + ((getPhotonum() == null) ? 0 : getPhotonum().hashCode());
        result = prime * result + ((getPtime() == null) ? 0 : getPtime().hashCode());
        result = prime * result + ((getFileid() == null) ? 0 : getFileid().hashCode());
        result = prime * result + ((getUptype() == null) ? 0 : getUptype().hashCode());
        result = prime * result + ((getUpmode() == null) ? 0 : getUpmode().hashCode());
        result = prime * result + ((getSyncStatus() == null) ? 0 : getSyncStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}