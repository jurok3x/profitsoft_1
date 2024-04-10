package com.ykotsiuba.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykotsiuba.entity.Article;
import com.ykotsiuba.entity.ArticleQueue;
import com.ykotsiuba.entity.ConcurrentParameterMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleProducer {

    private final ConcurrentParameterMap parameters;

    public ArticleProducer(ConcurrentParameterMap parameters) {
        this.parameters = parameters;
    }

    public void read(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            JsonFactory jFactory = new JsonFactory();
            JsonParser jParser = jFactory.createParser(reader);
            readJsonList(jParser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJsonList(JsonParser jParser) throws IOException {
        while (jParser.nextToken() != JsonToken.END_ARRAY) {
            String fieldname = jParser.getCurrentName();
            if ("author".equals(fieldname)) {
                jParser.nextToken();
                String parsedName = jParser.getText();
                parameters.add(parsedName);
            }
        }
        jParser.close();
    }
}
