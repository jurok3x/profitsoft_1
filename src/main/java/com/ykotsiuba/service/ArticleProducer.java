package com.ykotsiuba.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykotsiuba.entity.Article;
import com.ykotsiuba.entity.ArticleQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ArticleProducer {

    private final ArticleQueue articles;

    public ArticleProducer(ArticleQueue articles) {
        this.articles = articles;
    }

    public void read(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            readJsonList(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJsonList(BufferedReader reader) throws IOException {
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            char charRead = (char) nextChar;
            if (Character.isWhitespace(charRead)) {
                continue;
            }
            if (charRead == ']') {
                break;
            }
            Article article = readJsonObject(reader);
            articles.putArticle(article);
        }
    }

    private Article readJsonObject(BufferedReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder jsonBuilder = new StringBuilder();
        boolean inObject = false;
        int braceCount = 0;

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
