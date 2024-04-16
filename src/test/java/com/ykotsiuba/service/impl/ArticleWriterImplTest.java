package com.ykotsiuba.service.impl;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleWriterImplTest {
    private static final String NAME = "year";

    private ArticleWriter writer;

    private RuntimeParameters parameters;

    private ConcurrentParameterMap map;

    @BeforeEach
    void setUp() {
        parameters = mock(RuntimeParameters.class);
        map = mock(ConcurrentParameterMap.class);
        writer = new ArticleWriterImpl(map, parameters);
    }

    @AfterEach
    void tearDown() {
        String fileName = String.format("statistics_by_%s.xml", NAME);
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testWriteCreatesFileWithData() {
        Map<String, Integer> expectedMap = prepareMap();
        when(parameters.getParameterName()).thenReturn(NAME);
        when(map.getStatistics()).thenReturn(expectedMap);

        writer.write();

        String expectedFileName = String.format("statistics_by_%s.xml", NAME);

        File file = new File(expectedFileName);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    private Map<String, Integer> prepareMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("2022", 1);
        map.put("2020", 2);
        return map;
    }
}