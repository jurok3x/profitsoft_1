package com.ykotsiuba.service.impl;

import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.*;

class ExecutorImplTest {
    private static final int TASK_COUNT = 5;

    private Executor executor;

    private ArticleWriter writer;

    private ArticleReader reader;

    @BeforeEach
    void setUp() {
        writer = mock(ArticleWriter.class);
        reader = mock(ArticleReader.class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executor = new ExecutorImpl(reader, writer, executorService);
    }

    @Test
    void executeTest() {
        Runnable task = mock(Runnable.class);
        List<Runnable> tasks = prepareTasks(task);
        when(reader.read()).thenReturn(tasks);
        doNothing().when(writer).write();

        executor.run();

        verify(task, times(TASK_COUNT)).run();
        verify(writer, times(1)).write();
        verify(reader, times(1)).read();
    }

    private List<Runnable> prepareTasks(Runnable task) {
        List<Runnable> tasks = new ArrayList<>(TASK_COUNT);
        for (int i = 0; i < TASK_COUNT; i++) {
            tasks.add(task);
        }
        return tasks;
    }

}