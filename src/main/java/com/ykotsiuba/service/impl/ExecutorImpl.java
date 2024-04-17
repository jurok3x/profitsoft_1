package com.ykotsiuba.service.impl;

import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ExecutorImpl implements Executor {
    private final ExecutorService executorService;

    private final ArticleReader reader;

    private final ArticleWriter writer;

    public ExecutorImpl(ArticleReader reader, ArticleWriter writer, ExecutorService executorService) {
        this.reader = reader;
        this.writer = writer;
        this.executorService = executorService;
    }

    public void run() {
        System.out.println("Execution started...");

        List<Runnable> readTasks = reader.read();

        if(readTasks.isEmpty()) {
            System.out.println("No json files found.");
            return;
        }

        execute(readTasks);

        System.out.println("Execution finished.");
        executorService.shutdown();
    }

    private void execute(List<Runnable> readTasks) {
        CompletableFuture<?>[] futures = readTasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executorService))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        allOf.join();

        writer.write();
    }
}
