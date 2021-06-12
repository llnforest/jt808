package org.yzh.web.model.entity;

public class SysMenu {
    private Short id;

    private Short parentId;

    private String menuUrl;

    private String menuName;

    private String menuIcon;

    private String menuType;

    private String btnCss;

    private String btnFunc;

    private Byte btnType;

    private Byte logLevel;

    private String logRule;

    private Short sort;

    private String remark;

    private Byte status;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getParentId() {
        return parentId;
    }

    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getBtnCss() {
        return btnCss;
    }

    public void setBtnCss(String btnCss) {
        this.btnCss = btnCss;
    }

    public String getBtnFunc() {
        return btnFunc;
    }

    public void setBtnFunc(String btnFunc) {
        this.btnFunc = btnFunc;
    }

    public Byte getBtnType() {
        return btnType;
    }

    public void setBtnType(Byte btnType) {
        this.btnType = btnType;
    }

    public Byte getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Byte logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogRule() {
        return logRule;
    }

    public void setLogRule(String logRule) {
        this.logRule = logRule;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
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
        SysMenu other = (SysMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getMenuUrl() == null ? other.getMenuUrl() == null : this.getMenuUrl().equals(other.getMenuUrl()))
            && (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
            && (this.getMenuIcon() == null ? other.getMenuIcon() == null : this.getMenuIcon().equals(other.getMenuIcon()))
            && (this.getMenuType() == null ? other.getMenuType() == null : this.getMenuType().equals(other.getMenuType()))
            && (this.getBtnCss() == null ? other.getBtnCss() == null : this.getBtnCss().equals(other.getBtnCss()))
            && (this.getBtnFunc() == null ? other.getBtnFunc() == null : this.getBtnFunc().equals(other.getBtnFunc()))
            && (this.getBtnType() == null ? other.getBtnType() == null : this.getBtnType().equals(other.getBtnType()))
            && (this.getLogLevel() == null ? other.getLogLevel() == null : this.getLogLevel().equals(other.getLogLevel()))
            && (this.getLogRule() == null ? other.getLogRule() == null : this.getLogRule().equals(other.getLogRule()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getMenuUrl() == null) ? 0 : getMenuUrl().hashCode());
        result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
        result = prime * result + ((getMenuIcon() == null) ? 0 : getMenuIcon().hashCode());
        result = prime * result + ((getMenuType() == null) ? 0 : getMenuType().hashCode());
        result = prime * result + ((getBtnCss() == null) ? 0 : getBtnCss().hashCode());
        result = prime * result + ((getBtnFunc() == null) ? 0 : getBtnFunc().hashCode());
        result = prime * result + ((getBtnType() == null) ? 0 : getBtnType().hashCode());
        result = prime * result + ((getLogLevel() == null) ? 0 : getLogLevel().hashCode());
        result = prime * result + ((getLogRule() == null) ? 0 : getLogRule().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}