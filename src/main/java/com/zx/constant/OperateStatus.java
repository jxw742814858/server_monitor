package com.zx.constant;

public enum OperateStatus {
    LOG_ERR(-1, "登录异常"), OPER_ERR(-2, "操作异常"), NORMAL(0, "操作成功");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    OperateStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
