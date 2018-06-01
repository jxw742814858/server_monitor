package com.zx.mapper;

import com.zx.entity.MachineEntity;
import com.zx.entity.ServiceEntity;

import java.util.List;

public interface ServiceMapper {

    List<ServiceEntity> queryServList();

    void addMachine(MachineEntity entity);

    void addService(ServiceEntity entity);

    List<ServiceEntity> queryServListByOrderNum(int orderNum);

//    List<ServiceEntity> queryExctInfoBySids(String ids);
    List<ServiceEntity> queryExctInfoBySids(int[] ids);
}