package com.ykotsiuba.service;

import com.ykotsiuba.entity.Article;
import com.ykotsiuba.entity.ArticleQueue;

public class ArticleConsumer {
    private final ArticleQueue articles;

    public ArticleConsumer(ArticleQueue articles) {
        this.articles = articles;
    }

    public void onReceived() {
        while(true) {

            Article article = articles.getArticle();
            System.out.println("Received article " + article);
        }
    }
}
