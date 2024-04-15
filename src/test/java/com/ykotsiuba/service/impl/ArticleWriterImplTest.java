package com.ykotsiuba.service.impl;

import com.ykotsiuba.entity.ArticleStatistics;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.entity.StatisticsItem;
import com.ykotsiuba.service.ArticleWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static com.ykotsiuba.configuration.TestFieldsReader.read;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticleWriterImplTest {

    private static final String PATH = "C:\\";

    private static final String NAME = "year";

    private ArticleWriter writer;

    private RuntimeParameters parameters;

    private ConcurrentParameterMap map;

    @BeforeEach
    void setUp() {
        String[] args = new String[]{PATH, NAME};
        parameters = new RuntimeParameters(args);
        map = new ConcurrentParameterMap();
        initMap(map);
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
        writer.write();

        String expectedFileName = String.format("statistics_by_%s.xml", NAME);

        File file = new File(expectedFileName);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        ArticleStatistics articleStatistics = read(expectedFileName);
        List<StatisticsItem> statistics = articleStatistics.getStatistics();

        assertTrue(statistics.size() == map.getStatistics().size());
    }

    private void initMap(ConcurrentParameterMap map) {
        map.add("2022");
        map.add("2022");
        map.add("2024");
    }
}