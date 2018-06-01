package com.zx.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zx.entity.MachineEntity;
import com.zx.entity.ServiceEntity;

import java.util.List;

public interface Service {

    /**
     * 更新服务和所在机器信息
     */
    void updateServiceInfo();

    JSONArray querySerivces();

    /**
     * 获取服务状态
     * @return
     */
    JSONArray queryStatus();

    /**
     * 手动操作服务
     * TODO 等页面构建完成时，根据实际需要制定逻辑
     * @param cmdArr
     */
    void manualOperation(JSONArray cmdArr);

    /**
     * 自动服务列表启动
     * @param servStatus
     * @return
     */
    JSONArray autoOperation(JSONArray servStatus);

    /**
     * 服务器添加
     * @param entity
     */
    JSONObject addMachine(MachineEntity entity);

    /**
     * 服务添加
     * @param entity
     * @return
     */
    JSONObject addService(ServiceEntity entity);

    /**
     * 根据orderNum查询服务列表
     * @param order_num
     * @return
     */
    List<ServiceEntity> queryByOrderNum(int order_num);

    /**
     * 批量服务状态查询 (返回状态集合)
     * @param ids
     * @return
     */
    JSONArray batchQueryStatus(int[] ids);

    /**
     * 服务批量启动 (返回状态集合)
     * @param ids
     * @return
     */
    JSONArray batchStart(int[] ids);

    /**
     * 批量服务状态查询 (打印)
     * @param ids
     * @return
     */
    String batchQueryStatusForPrint(int[] ids);

    /**
     * 服务批量启动 (打印)
     * @param ids
     * @return
     */
    String batchStartForPrint(int[] ids);
}