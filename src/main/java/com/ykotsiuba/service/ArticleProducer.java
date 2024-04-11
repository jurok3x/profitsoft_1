package com.ykotsiuba.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArticleProducer {

    private final ConcurrentParameterMap parameterMap;

    private final RuntimeParameters parameters;

    public ArticleProducer(ConcurrentParameterMap parameterMap, RuntimeParameters parameters) {
        this.parameterMap = parameterMap;
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
            String parameter = parameters.getParameterName();
            if (parameter.equals(fieldname)) {
                jParser.nextToken();
                String parsedName = jParser.getText();
                parameterMap.add(parsedName);
            }
        }
        jParser.close();
    }
}
