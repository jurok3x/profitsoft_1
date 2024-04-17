package com.ykotsiuba.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.ykotsiuba.entity.Article;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestFilesGenerator {

    public static final String DATA_FOLDER = "src/test/resources/data";
    private static final ObjectMapper DEFAULT_MAPPER;

    static {
        createFolderIfNotExists(DATA_FOLDER);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL.NON_NULL);
        DEFAULT_MAPPER = mapper;
    }

    private static final int START_YEAR = 1999;
    private static final int END_YEAR = 2024;

    /**
     * Writes the provided articles to a JSON file.
     *
     * @param articles The articles to write to the JSON file.
     */
    public static void writeJson(Article ...articles) {
        try {
            List<Article> articlesList = Arrays.stream(articles).toList();
            File outFile = prepareOutFile();
            DEFAULT_MAPPER.writeValue(outFile, articlesList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * Generates a random JSON file containing the specified number of articles.
     *
     * @param size The number of articles to generate.
     */
    public static void generateRandomJson(int size) {
        try {
            List<Article> articles = prepareArticles(size);
            File outFile = prepareOutFile();
            DEFAULT_MAPPER.writeValue(outFile, articles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates multiple random JSON files, each containing the specified number of articles.
     *
     * @param objectsCount The number of articles to generate in each file.
     * @param filesCount   The number of files to generate.
     */
    public static void generateMultipleRandomJson(int objectsCount, int filesCount) {
        for (int i = 0; i < filesCount; i++) {
            generateRandomJson(objectsCount);
        }
    }

    /**
     * Deletes all JSON files in the test data folder.
     */
    public static void clean() {
        try {
            File resourceFolder = new File(DATA_FOLDER);
            if(resourceFolder.listFiles() == null) {
                return;
            }
            for (File file : resourceFolder.listFiles()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static File prepareOutFile() throws IOException {
        File resourceFolder = new File(DATA_FOLDER);
        int length = resourceFolder.listFiles() != null ? resourceFolder.listFiles().length : 1;
        String fileName = String.format("test_%d.json", length);
        File outFile = new File(resourceFolder, fileName);
        outFile.createNewFile();
        return outFile;
    }

    /**
     * Prepares a list of articles with random data.
     *
     * @param size The number of articles to prepare.
     * @return The list of prepared articles.
     */
    private static List<Article> prepareArticles(int size) {
        List<Article> articles = new ArrayList<>(size);
        Faker faker = new Faker();
        for (int i = 0; i < size; i++) {
            articles.add(
                    Article.builder()
                            .title(faker.lorem().sentence())
                            .author(faker.name().fullName())
                            .year(faker.number().numberBetween(START_YEAR, END_YEAR))
                            .journal(faker.book().publisher())
                            .field(faker.regexify(prepareFields()))
                            .build()
            );
        };
        return articles;
    }

    private static String prepareFields() {
        return Arrays.stream(TestFields.values())
                .map(value -> value.toString().toLowerCase())
                .collect(Collectors.joining("|"));
    }
}
