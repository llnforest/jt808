package org.yzh.web.model.entity;

import java.util.Date;

public class JsClassrecord {
    private Integer id;

    private String inscode;

    private String stunum;

    private String coachnum;

    private String carnum;

    private String simunum;

    private String platnum;

    private String recnum;

    private String subjcode;

    private String photo1;

    private String photo2;

    private String photo3;

    private String starttime;

    private String endtime;

    private String duration;

    private String mileage;

    private String avevelocity;

    private String coacmt;

    private String total;

    private String part1;

    private String part2;

    private String part3;

    private String part4;

    private Integer status;

    private Integer syncStatus;

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

    public String getSimunum() {
        return simunum;
    }

    public void setSimunum(String simunum) {
        this.simunum = simunum;
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

    public String getSubjcode() {
        return subjcode;
    }

    public void setSubjcode(String subjcode) {
        this.subjcode = subjcode;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
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

    public String getCoacmt() {
        return coacmt;
    }

    public void setCoacmt(String coacmt) {
        this.coacmt = coacmt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPart1() {
        return part1;
    }

    public void setPart1(String part1) {
        this.part1 = part1;
    }

    public String getPart2() {
        return part2;
    }

    public void setPart2(String part2) {
        this.part2 = part2;
    }

    public String getPart3() {
        return part3;
    }

    public void setPart3(String part3) {
        this.part3 = part3;
    }

    public String getPart4() {
        return part4;
    }

    public void setPart4(String part4) {
        this.part4 = part4;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
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
        JsClassrecord other = (JsClassrecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getStunum() == null ? other.getStunum() == null : this.getStunum().equals(other.getStunum()))
            && (this.getCoachnum() == null ? other.getCoachnum() == null : this.getCoachnum().equals(other.getCoachnum()))
            && (this.getCarnum() == null ? other.getCarnum() == null : this.getCarnum().equals(other.getCarnum()))
            && (this.getSimunum() == null ? other.getSimunum() == null : this.getSimunum().equals(other.getSimunum()))
            && (this.getPlatnum() == null ? other.getPlatnum() == null : this.getPlatnum().equals(other.getPlatnum()))
            && (this.getRecnum() == null ? other.getRecnum() == null : this.getRecnum().equals(other.getRecnum()))
            && (this.getSubjcode() == null ? other.getSubjcode() == null : this.getSubjcode().equals(other.getSubjcode()))
            && (this.getPhoto1() == null ? other.getPhoto1() == null : this.getPhoto1().equals(other.getPhoto1()))
            && (this.getPhoto2() == null ? other.getPhoto2() == null : this.getPhoto2().equals(other.getPhoto2()))
            && (this.getPhoto3() == null ? other.getPhoto3() == null : this.getPhoto3().equals(other.getPhoto3()))
            && (this.getStarttime() == null ? other.getStarttime() == null : this.getStarttime().equals(other.getStarttime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getMileage() == null ? other.getMileage() == null : this.getMileage().equals(other.getMileage()))
            && (this.getAvevelocity() == null ? other.getAvevelocity() == null : this.getAvevelocity().equals(other.getAvevelocity()))
            && (this.getCoacmt() == null ? other.getCoacmt() == null : this.getCoacmt().equals(other.getCoacmt()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getPart1() == null ? other.getPart1() == null : this.getPart1().equals(other.getPart1()))
            && (this.getPart2() == null ? other.getPart2() == null : this.getPart2().equals(other.getPart2()))
            && (this.getPart3() == null ? other.getPart3() == null : this.getPart3().equals(other.getPart3()))
            && (this.getPart4() == null ? other.getPart4() == null : this.getPart4().equals(other.getPart4()))
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
        result = prime * result + ((getSimunum() == null) ? 0 : getSimunum().hashCode());
        result = prime * result + ((getPlatnum() == null) ? 0 : getPlatnum().hashCode());
        result = prime * result + ((getRecnum() == null) ? 0 : getRecnum().hashCode());
        result = prime * result + ((getSubjcode() == null) ? 0 : getSubjcode().hashCode());
        result = prime * result + ((getPhoto1() == null) ? 0 : getPhoto1().hashCode());
        result = prime * result + ((getPhoto2() == null) ? 0 : getPhoto2().hashCode());
        result = prime * result + ((getPhoto3() == null) ? 0 : getPhoto3().hashCode());
        result = prime * result + ((getStarttime() == null) ? 0 : getStarttime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getMileage() == null) ? 0 : getMileage().hashCode());
        result = prime * result + ((getAvevelocity() == null) ? 0 : getAvevelocity().hashCode());
        result = prime * result + ((getCoacmt() == null) ? 0 : getCoacmt().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getPart1() == null) ? 0 : getPart1().hashCode());
        result = prime * result + ((getPart2() == null) ? 0 : getPart2().hashCode());
        result = prime * result + ((getPart3() == null) ? 0 : getPart3().hashCode());
        result = prime * result + ((getPart4() == null) ? 0 : getPart4().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}