package com.mintegral.detailroi.common.threadpool;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author songjunjun
 * @date 2019/3/6
 **/
public class ThreadPoolUtils {

    public static ThreadPoolExecutor commonThreadPoolExecutor;
    private static ThreadPoolExecutor reportThreadPool;

    public static ThreadPoolExecutor getCommonThreadPool(){
        if (commonThreadPoolExecutor == null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread thread =  Executors.defaultThreadFactory().newThread(r);
                    thread.setName("CommonThreadPool");
                    return thread;
                }
            };
             commonThreadPoolExecutor = new ThreadPoolExecutor(5,
                     200,
                     15L,
                     TimeUnit.MILLISECONDS,
                     new SynchronousQueue<Runnable>(),
                     threadFactory,
                     new ThreadPoolExecutor.DiscardPolicy());
        }

        return commonThreadPoolExecutor;
    }



    public static ThreadPoolExecutor getReportThreadPool(){
        if (reportThreadPool == null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread thread =  Executors.defaultThreadFactory().newThread(r);
                    thread.setName("ReportThreadPool");
                    return thread;
                }
            };
            reportThreadPool = new ThreadPoolExecutor(5,
                    200,
                    5L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(60),
                    threadFactory,
                    new ThreadPoolExecutor.DiscardPolicy());
        }

        return reportThreadPool;

    }
}
