package com.ykotsiuba.service.impl;

import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorImpl implements Executor {

    private final ExecutorService executorService;

    private final ArticleReader reader;

    private final ArticleWriter writer;

    public ExecutorImpl(ArticleReader reader, ArticleWriter writer) {
        this.reader = reader;
        this.writer = writer;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void run() {
        List<Runnable> readTasks = reader.read();

        CompletableFuture<?>[] futures = readTasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executorService))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        allOf.join();

        writer.write();

        executorService.shutdown();
    }
}
