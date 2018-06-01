package com.zx.runner;

import com.zx.service.Service;
import com.zx.thread.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SchedulerRunner extends ThreadPool implements ApplicationRunner {

    @Autowired
    Service service;

    /**
     * 配置初始化服务信息列表
     *
     * @param applicationArguments
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
//        singleThread.execute(() -> service.updateServiceInfo());
        scheduled.scheduleWithFixedDelay(() -> service.updateServiceInfo(), 0, 10, TimeUnit.MINUTES);
    }
}
