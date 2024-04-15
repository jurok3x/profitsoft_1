package com.ykotsiuba.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ykotsiuba.entity.ArticleStatistics;

import java.io.File;
import java.io.IOException;

public class TestFieldsReader {

    private static final ObjectMapper DEFAULT_READER;

    static {
        DEFAULT_READER = new XmlMapper();
    }

    public static ArticleStatistics read(String fileName) {
        try {
            ArticleStatistics articleStatistics = DEFAULT_READER.readValue(new File(fileName), ArticleStatistics.class);
            return articleStatistics;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
