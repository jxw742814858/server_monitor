package com.zx.entity;

import java.io.Serializable;

/**
 * 服务信息实体
 */
public class ServiceEntity implements Serializable {

    private String host;
    private String account;
    private String passwd;

    private Integer sid;
    private Integer mid;
    private String service_name;
    private String process_name;
    private String path;
    private String depict;
    private String cmad_start;
    private String cmad_restart;
    private String cmad_stop;
    private String cmad_status;
    private Integer order_num;

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

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public String getCmad_start() {
        return cmad_start;
    }

    public void setCmad_start(String cmad_start) {
        this.cmad_start = cmad_start;
    }

    public String getCmad_restart() {
        return cmad_restart;
    }

    public void setCmad_restart(String cmad_restart) {
        this.cmad_restart = cmad_restart;
    }

    public String getCmad_stop() {
        return cmad_stop;
    }

    public void setCmad_stop(String cmad_stop) {
        this.cmad_stop = cmad_stop;
    }

    public String getCmad_status() {
        return cmad_status;
    }

    public void setCmad_status(String cmad_status) {
        this.cmad_status = cmad_status;
    }

    public Integer getOrder_num() {
        return order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }
}
