package com.ykotsiuba.service;

import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.utils.FileSystemUtils;

import java.util.List;
import java.util.concurrent.*;

public class ParallelExecutorService {

    private final ExecutorService executorService;

    private final ArticleReader reader;

    private final ArticleWriter writer;

    public ParallelExecutorService(RuntimeParameters parameters, ArticleReader reader, ArticleWriter writer) {
        this.parameters = parameters;
        this.reader = reader;
        this.writer = writer;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void run() {
        List<String> jsonFiles = FileSystemUtils.getJsonFiles(parameters.getFolderPath());
        CompletableFuture<?>[] futures = jsonFiles.stream()
                .map(file -> CompletableFuture.runAsync(() -> reader.read(file,
                        parameters.getParameterName()), executorService))
                .toArray(CompletableFuture[]::new);


        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        allOf.join();

        writer.write();

        executorService.shutdown();
    }
}
