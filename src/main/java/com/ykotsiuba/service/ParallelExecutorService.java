package com.ykotsiuba.service;

import com.ykotsiuba.entity.ArticleQueue;
import com.ykotsiuba.utils.FileSystemUtils;

import java.util.List;
import java.util.concurrent.*;

public class ParallelExecutorService {

    private ExecutorService executorService;

    private ArticleConsumer consumer;

    private ArticleProducer producer;

    private ArticleQueue articles;

    public ParallelExecutorService() {
        this.articles = new ArticleQueue();
        this.consumer = new ArticleConsumer(articles);
        this.producer = new ArticleProducer(articles);
        int corePoolSize = 5; // Number of threads to keep in the pool, even if they are idle
        int maximumPoolSize = 10; // Maximum number of threads to allow in the pool
        long keepAliveTime = 60; // Time limit for which threads may remain idle before being terminated
        TimeUnit unit = TimeUnit.SECONDS; // Unit of the keep alive time
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); // The queue to use for holding tasks before they are executed
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public void run() {
        List<String> jsonFiles = FileSystemUtils.getJsonFiles("D:\\");
        for(String file: jsonFiles) {
            executorService.execute(() -> producer.read("D:\\" + file));
        }
        executorService.execute(() -> consumer.onReceived());
    }
}
