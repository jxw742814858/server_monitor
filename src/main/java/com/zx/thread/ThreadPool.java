package com.zx.thread;

import com.zx.constant.Constant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadPool {

    protected ExecutorService fixedThreadPool;
//    protected ExecutorService singleThread;
    protected ScheduledExecutorService scheduled;

    public ThreadPool() {
        fixedThreadPool = Executors.newFixedThreadPool(Constant.MAX_THREAD_CNT);
//        singleThread = Executors.newSingleThreadExecutor();
        scheduled = new ScheduledThreadPoolExecutor(1);
    }
}
