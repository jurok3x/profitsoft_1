package com.ykotsiuba.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.utils.FileSystemUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ArticleReaderImpl implements ArticleReader {

    private final ConcurrentParameterMap parameterMap;

    private final RuntimeParameters parameters;

    public ArticleReaderImpl(ConcurrentParameterMap parameterMap, RuntimeParameters parameters) {
        this.parameterMap = parameterMap;
        this.parameters = parameters;
    }

    public List<Runnable> read() {
        List<String> jsonFiles = FileSystemUtils.getJsonFiles(parameters.getFolderPath());
        return jsonFiles.stream()
                .map(file -> createReadTask(file))
                .toList();
    }

    private Runnable createReadTask(String filePath) {
        return () -> readJsonFile(filePath);
    }

    private void readJsonFile(String fileName) {
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
