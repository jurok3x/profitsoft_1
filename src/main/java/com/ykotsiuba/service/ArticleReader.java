package com.ykotsiuba.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ykotsiuba.entity.ConcurrentParameterMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArticleReader {

    private final ConcurrentParameterMap parameterMap;

    public ArticleReader(ConcurrentParameterMap parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void read(String fileName, String parameter) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            JsonFactory jFactory = new JsonFactory();
            JsonParser jParser = jFactory.createParser(reader);
            readJsonList(jParser, parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJsonList(JsonParser jParser, String parameter) throws IOException {
        while (jParser.nextToken() != JsonToken.END_ARRAY) {
            String fieldname = jParser.getCurrentName();
            if (parameter.equals(fieldname)) {
                jParser.nextToken();
                String parsedName = jParser.getText();
                parameterMap.add(parsedName);
            }
        }
        jParser.close();
    }
}
