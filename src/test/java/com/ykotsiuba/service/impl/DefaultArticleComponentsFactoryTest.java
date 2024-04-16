package com.ykotsiuba.service.impl;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultArticleComponentsFactoryTest {

    private static final String PATH = "C:\\";

    private static final String NAME = "year";

    private ArticleComponentsFactory factory;

    @BeforeEach
    void setUp() {
        String[] args = new String[]{PATH, NAME};
        factory = DefaultArticleComponentsFactory.getInstance(args);
    }

    @Test
    void createExecutorTest() {
        Executor executor = factory.create(Executor.class);
        assertNotNull(executor);
    }

    @Test
    void createExecutorSingletoneTest() {
        Executor executor = factory.create(Executor.class);
        Executor executor2 = factory.create(Executor.class);
        assertEquals(executor, executor2);
    }

    @Test
    void createReaderTest() {
        ArticleReader reader = factory.create(ArticleReader.class);
        assertNotNull(reader);
    }

    @Test
    void createWriterTest() {
        ArticleWriter writer = factory.create(ArticleWriter.class);
        assertNotNull(writer);
    }

    @Test
    void createMapTest() {
        ConcurrentParameterMap map = factory.create(ConcurrentParameterMap.class);
        assertNotNull(map);
    }

    @Test
    void nullTest() {
        String string = factory.create(String.class);
        assertNull(string);
    }

}