package org.yzh.web.model.entity;

public class JsPlatInfo {
    private Integer id;

    private String platNum;

    private String password;

    private Integer joinCode;

    private Integer tcpStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatNum() {
        return platNum;
    }

    public void setPlatNum(String platNum) {
        this.platNum = platNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(Integer joinCode) {
        this.joinCode = joinCode;
    }

    public Integer getTcpStatus() {
        return tcpStatus;
    }

    public void setTcpStatus(Integer tcpStatus) {
        this.tcpStatus = tcpStatus;
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
        JsPlatInfo other = (JsPlatInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlatNum() == null ? other.getPlatNum() == null : this.getPlatNum().equals(other.getPlatNum()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getJoinCode() == null ? other.getJoinCode() == null : this.getJoinCode().equals(other.getJoinCode()))
            && (this.getTcpStatus() == null ? other.getTcpStatus() == null : this.getTcpStatus().equals(other.getTcpStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlatNum() == null) ? 0 : getPlatNum().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getJoinCode() == null) ? 0 : getJoinCode().hashCode());
        result = prime * result + ((getTcpStatus() == null) ? 0 : getTcpStatus().hashCode());
        return result;
    }
}