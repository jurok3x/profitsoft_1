package com.ykotsiuba;

import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.Executor;
import com.ykotsiuba.service.impl.DefaultArticleComponentsFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.ykotsiuba.configuration.TestFilesGenerator.clean;
import static com.ykotsiuba.configuration.TestFilesGenerator.generateMultipleRandomJson;

/**
 * Use this test to evaluate performance of this app.
 * Specify number of files and number of JSON objects in single file.
 * You can also change threadNumber in test app.properies.
 */
public class PerformanceTest {
    private static final int OBJECTS_COUNT = 100;

    private static final int FILES_COUNT = 200;

    private Executor service;

    private static final String PATH = "src/test/resources/data";

    private static final String NAME = "year";

    @BeforeEach
    void setUp() {
        System.out.printf("Preparing %d json files with %d objects...%n", FILES_COUNT, OBJECTS_COUNT);
        generateMultipleRandomJson(OBJECTS_COUNT, FILES_COUNT);
        String[] args = {PATH, NAME};
        ArticleComponentsFactory factory = DefaultArticleComponentsFactory.getInstance(args);
        service = factory.create(Executor.class);
    }

    @AfterEach
    void tearDown() {
        clean();
        String fileName = String.format("statistics_by_%s.xml", NAME);
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
    }

    @Test
    @Disabled
    public void testApp() {
        long startTime = System.nanoTime();
        service.run();
        long endTime = System.nanoTime();
        long executionTimeNano = endTime - startTime;
        double executionTimeMillis = (double) executionTimeNano / 1_000_000;

        System.out.println("Execution time: " + executionTimeMillis + " milliseconds");
    }
}
