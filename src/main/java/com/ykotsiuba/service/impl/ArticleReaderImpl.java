package com.ykotsiuba.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.ykotsiuba.utils.FileSystemUtils.getJsonFiles;

/**
 * The ArticleReaderImpl class implements the ArticleReader interface
 * and provides functionality to read article data from JSON files.
 */
public class ArticleReaderImpl implements ArticleReader {

    private final ConcurrentParameterMap parameterMap;

    private final RuntimeParameters parameters;

    public ArticleReaderImpl(ConcurrentParameterMap parameterMap, RuntimeParameters parameters) {
        this.parameterMap = parameterMap;
        this.parameters = parameters;
    }

    /**
     * Reads article data from JSON files in the specified folder path.
     * Each JSON file is processed asynchronously, and a list of Runnables representing
     * tasks to process the read article data is returned.
     *
     * @return A list of Runnables representing tasks to process the read article data.
     */
    public List<Runnable> read() {
        List<String> jsonFiles = getJsonFiles(parameters.getFolderPath());
        return jsonFiles.stream()
                .map(file -> createReadTask(file))
                .toList();
    }

    private Runnable createReadTask(String filePath) {
        return () -> readJsonFile(filePath);
    }

    /**
     * Reads the contents of a JSON file and updates the parameter map with the extracted data.
     *
     * @param fileName The name of the JSON file to read.
     */
    private void readJsonFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            JsonFactory jFactory = new JsonFactory();
            JsonParser jParser = jFactory.createParser(reader);
            readJsonList(jParser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a JSON array from the provided JSON parser and updates the parameter map with the extracted data.
     *
     * @param jParser The JSON parser.
     * @throws IOException If an I/O error occurs during JSON parsing.
     */
    private void readJsonList(JsonParser jParser) throws IOException {
        while (jParser.nextToken() != JsonToken.END_ARRAY) {
            String fieldname = jParser.getCurrentName();
            String parameter = parameters.getParameterName();
            if (parameter.equals(fieldname)) {
                jParser.nextToken();
                String parsedName = jParser.getText();
                addParameter(parsedName);
            }
        }
        jParser.close();
    }

    private void addParameter(String parsedName) {
        if (parsedName.contains(",")) {
            String[] names = parsedName.split("\\s*,\\s*");
            for (String name : names) {
                parameterMap.add(name.trim());
            }
        } else {
            parameterMap.add(parsedName);
        }
    }
}
