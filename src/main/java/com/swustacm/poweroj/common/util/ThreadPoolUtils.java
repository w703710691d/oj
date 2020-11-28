package com.swustacm.poweroj.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xingzi
 * 线程池
 */
@Component
public class ThreadPoolUtils {

    @Autowired
    RedisUtils redisUtils;
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 200, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000),
            r -> new Thread(r, "gis_setting" + r.hashCode()),
            new ThreadPoolExecutor.DiscardOldestPolicy());
    /**
     * 获取线程池
     * @return
     */
    public ThreadPoolExecutor getExecutor(){
        return executor;
    }

}
