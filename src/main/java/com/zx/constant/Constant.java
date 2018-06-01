package com.zx.constant;

import com.zx.kit.PropReader;

import java.util.Properties;

public class Constant {
    static Properties prop = PropReader.load("config.properties");

    /**
     * 线程池最大线程数
     */
    public static final Integer MAX_THREAD_CNT = Integer.valueOf(prop.getProperty("max.thread.cnt"));
}
