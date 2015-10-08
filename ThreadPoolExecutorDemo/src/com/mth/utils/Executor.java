package com.mth.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class Executor {

    private static ThreadPoolExecutor poolExecutor;

    static {
        int corePoolSize = 1;
        int maximumPoolSize = 1;
        long keepAliveTime = 1;
        BlockingQueue queue = new PriorityBlockingQueue<Runnable>();
        poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);
    }

    /**
     * 执行
     * 
     * @param run
     * @param name
     * @param priority
     */
    public static void executTask(Runnable run, String name, int priority) {
        poolExecutor.execute(run);
    }

    /**
     * 移除一个未执行的任务
     * 
     * @param run
     */
    public static void removeTask(Runnable run) {
        boolean remove = poolExecutor.getQueue().remove(run);
        Log.e("MTH", remove + "");
    }

}
