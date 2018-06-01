package com.zx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zx.constant.OperateStatus;
import com.zx.entity.MachineEntity;
import com.zx.entity.ServiceEntity;
import com.zx.kit.ParamFormat;
import com.zx.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    Service service;

    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public JSONArray queryList() {
        return service.queryStatus();
    }

    @RequestMapping(value = "/queryServices", method = RequestMethod.GET)
    public JSONArray queryServices() {
        return service.querySerivces();
    }

    @RequestMapping(value = "/updateServ", method = RequestMethod.GET)
    public void updateServ() {
        service.updateServiceInfo();
    }

    @RequestMapping(value = "/autoStartServ", method = RequestMethod.GET)
    public JSONArray autoStartServ() {
        JSONArray statusArr = service.queryStatus();
        return service.autoOperation(statusArr);
    }

    /**
     * machine model: {"host":"","account":"","passwd":"","area":"","type":1,"status":1}
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/addMachine", method = RequestMethod.POST)
    public JSONObject addMachine(@RequestBody MachineEntity entity) {
        if (entity == null)
            return ParamFormat.parseResult(OperateStatus.LOG_ERR);
        // test
        if (true)
            return null;
        return service.addMachine(entity);
    }

    /**
     * service model: {"host":"","account":"","passwd":"","sid":0,"mid":1,"service_name":"","process_name":"","path":"","depict":"","cmad_start":"","cmad_restart":"","cmad_stop":"","cmad_status":"", "order_num": 0}
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/addService", method = RequestMethod.POST)
    public JSONObject addService(@RequestBody ServiceEntity entity) {
        if (entity == null)
            return ParamFormat.parseResult(OperateStatus.OPER_ERR);
        return service.addService(entity);
    }

    @RequestMapping(value = "/queryByOrdNum", method = RequestMethod.POST)
    public List<ServiceEntity> queryByOrdNum(@RequestBody int orderNum) {
        return service.queryByOrderNum(orderNum);
    }

    @RequestMapping(value = "/batchQueryStatus", method = RequestMethod.POST)
    public JSONArray batchQueryStatus(@RequestBody int[] ids) {
        return service.batchQueryStatus(ids);
    }

    @RequestMapping(value = "/batchStart", method = RequestMethod.POST)
    public JSONArray batchStart(@RequestBody int[] ids) {
        return service.batchStart(ids);
    }
}
