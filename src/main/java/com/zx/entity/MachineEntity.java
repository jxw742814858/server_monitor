package com.zx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务器信息实体
 */
public class MachineEntity implements Serializable {

    private Integer mid;
    private String host;
    private String account;
    private String passwd;
    private String area;
    private Integer type;
    private Integer status;
    private Date create_time;
    private Date ban_time;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getBan_time() {
        return ban_time;
    }

    public void setBan_time(Date ban_time) {
        this.ban_time = ban_time;
    }
}
