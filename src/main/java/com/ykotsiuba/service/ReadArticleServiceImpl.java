package com.ykotsiuba.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykotsiuba.entity.Article;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadArticleServiceImpl {

    public List<Article> read(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            List<Article> articles = readJsonList(reader);
            return articles;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Article> readJsonList(BufferedReader reader) throws IOException {
        int nextChar;
        List<Article> articles = new ArrayList<>();
        while ((nextChar = reader.read()) != -1) {
            char charRead = (char) nextChar;
            if (Character.isWhitespace(charRead)) {
                continue;
            }
            if (charRead == ']') {
                break;
            }
            Article article = readJsonObject(reader);
            articles.add(article);
        }
        return articles;
    }

    private Article readJsonObject(BufferedReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder jsonBuilder = new StringBuilder();
        boolean inObject = false; // Flag to track whether we are inside a JSON object
        int braceCount = 0; // Counter for open and closing braces

        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            char charRead = (char) nextChar;

            if (inObject) {
                jsonBuilder.append(charRead);
                if (charRead == '{') {
                    braceCount++;
                } else if (charRead == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        break;
                    }
                }
            } else if (charRead == '{') {
                inObject = true;
                jsonBuilder.append(charRead);
                braceCount++;
            }
        }

        String json = jsonBuilder.toString();
        Article article = mapper.readValue(json, Article.class);
        return article;
    }
}
