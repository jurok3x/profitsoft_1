package com.ykotsiuba;

import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.Executor;
import com.ykotsiuba.service.impl.DefaultArticleComponentsFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ykotsiuba.configuration.TestFilesGenerator.*;

public class PerformanceTest {
    private static final Integer ARTICLE_COUNT = 5;
    private static final Integer YEAR = 2024;

    private Executor service;

    private static final String PATH = "src/test/resources/data";

    private static final String NAME = "year";

    @BeforeEach
    void setUp() {
        String[] args = {PATH, NAME};
        ArticleComponentsFactory factory = DefaultArticleComponentsFactory.getInstance(args);
        service = factory.create(Executor.class);
        generateMultipleRandomJson(100, 200);
    }

    @AfterEach
    void tearDown() {
        clean();
    }

    @Test
    public void testApp() {
        long startTime = System.nanoTime();
        service.run();
        long endTime = System.nanoTime();
        long executionTimeNano = endTime - startTime;
        double executionTimeMillis = (double) executionTimeNano / 1_000_000;

        System.out.println("Execution time: " + executionTimeMillis + " milliseconds");
    }
}
