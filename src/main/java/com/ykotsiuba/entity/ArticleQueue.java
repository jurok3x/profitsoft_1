package com.ykotsiuba.entity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ArticleQueue {

    private final BlockingQueue<Article> queue;

    public ArticleQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Put a article into the queue.
     *
     * @param article The article to be added to the queue.
     */
    public void putArticle(Article article) {
        try {
            queue.put(article);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a article from the queue.
     *
     * @return The article retrieved from the queue.
     */
    public Article getArticle() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
