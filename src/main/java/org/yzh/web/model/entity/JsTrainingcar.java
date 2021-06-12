package org.yzh.web.model.entity;

import java.util.Date;

public class JsTrainingcar {
    private Integer id;

    private String carnum;

    private String inscode;

    private String franum;

    private String engnum;

    private String licnum;

    private String platecolor;

    private String photo;

    private String manufacture;

    private String brand;

    private String model;

    private String perdritype;

    private String buydate;

    private Integer sort;

    private Byte syncStatus;

    private Date createTime;

    private Date updateTime;

    private String deleteTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
    }

    public String getFranum() {
        return franum;
    }

    public void setFranum(String franum) {
        this.franum = franum;
    }

    public String getEngnum() {
        return engnum;
    }

    public void setEngnum(String engnum) {
        this.engnum = engnum;
    }

    public String getLicnum() {
        return licnum;
    }

    public void setLicnum(String licnum) {
        this.licnum = licnum;
    }

    public String getPlatecolor() {
        return platecolor;
    }

    public void setPlatecolor(String platecolor) {
        this.platecolor = platecolor;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPerdritype() {
        return perdritype;
    }

    public void setPerdritype(String perdritype) {
        this.perdritype = perdritype;
    }

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Byte syncStatus) {
        this.syncStatus = syncStatus;
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

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
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
        JsTrainingcar other = (JsTrainingcar) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCarnum() == null ? other.getCarnum() == null : this.getCarnum().equals(other.getCarnum()))
            && (this.getInscode() == null ? other.getInscode() == null : this.getInscode().equals(other.getInscode()))
            && (this.getFranum() == null ? other.getFranum() == null : this.getFranum().equals(other.getFranum()))
            && (this.getEngnum() == null ? other.getEngnum() == null : this.getEngnum().equals(other.getEngnum()))
            && (this.getLicnum() == null ? other.getLicnum() == null : this.getLicnum().equals(other.getLicnum()))
            && (this.getPlatecolor() == null ? other.getPlatecolor() == null : this.getPlatecolor().equals(other.getPlatecolor()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getManufacture() == null ? other.getManufacture() == null : this.getManufacture().equals(other.getManufacture()))
            && (this.getBrand() == null ? other.getBrand() == null : this.getBrand().equals(other.getBrand()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getPerdritype() == null ? other.getPerdritype() == null : this.getPerdritype().equals(other.getPerdritype()))
            && (this.getBuydate() == null ? other.getBuydate() == null : this.getBuydate().equals(other.getBuydate()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getSyncStatus() == null ? other.getSyncStatus() == null : this.getSyncStatus().equals(other.getSyncStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarnum() == null) ? 0 : getCarnum().hashCode());
        result = prime * result + ((getInscode() == null) ? 0 : getInscode().hashCode());
        result = prime * result + ((getFranum() == null) ? 0 : getFranum().hashCode());
        result = prime * result + ((getEngnum() == null) ? 0 : getEngnum().hashCode());
        result = prime * result + ((getLicnum() == null) ? 0 : getLicnum().hashCode());
        result = prime * result + ((getPlatecolor() == null) ? 0 : getPlatecolor().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getManufacture() == null) ? 0 : getManufacture().hashCode());
        result = prime * result + ((getBrand() == null) ? 0 : getBrand().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getPerdritype() == null) ? 0 : getPerdritype().hashCode());
        result = prime * result + ((getBuydate() == null) ? 0 : getBuydate().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getSyncStatus() == null) ? 0 : getSyncStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }
}