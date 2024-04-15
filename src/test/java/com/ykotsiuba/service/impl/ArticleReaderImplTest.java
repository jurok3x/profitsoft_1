package com.ykotsiuba.service.impl;

import com.ykotsiuba.configuration.TestFilesGenerator;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.ArticleReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ykotsiuba.configuration.TestFilesGenerator.clean;
import static com.ykotsiuba.configuration.TestFilesGenerator.generateMultipleRandomJson;
import static com.ykotsiuba.utils.FileSystemUtils.getJsonFiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ArticleReaderImplTest {

    private static final String PATH = "src/test/resources/data";

    private static final String NAME = "year";

    private static final int FILES_COUNT = 10;

    private ArticleReader reader;

    @BeforeEach
    void setUp() {
        String[] args = new String[]{PATH, NAME};
        ArticleComponentsFactory factory = DefaultArticleComponentsFactory.getInstance(args);
        reader = factory.create(ArticleReader.class);
        generateMultipleRandomJson(1, FILES_COUNT);
    }

    @AfterEach
    void tearDown() {
        clean();
    }

    @Test
    public void testPrepareReadTasks() {
        List<Runnable> runnableList = reader.read();
        assertEquals(FILES_COUNT, runnableList.size());
    }

}