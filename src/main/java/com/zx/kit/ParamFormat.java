package com.zx.kit;

import com.alibaba.fastjson.JSONObject;
import com.zx.constant.OperateStatus;

public class ParamFormat {

    public static JSONObject parseResult(OperateStatus status) {
        JSONObject resObj = new JSONObject();
        resObj.put("code", status.getCode());
        resObj.put("desc", status.getDesc());

        return resObj;
    }
}
