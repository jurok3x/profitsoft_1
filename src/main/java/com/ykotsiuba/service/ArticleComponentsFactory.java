package com.ykotsiuba.service;

import com.ykotsiuba.entity.ConcurrentParameterMap;

import java.util.concurrent.ExecutorService;

public interface ArticleComponentsFactory {

    /**
     * Creates an instance of the specified class.
     *
     * @param clazz The class of the component to create. Supported classes:
     *              <ul>
     *                  <li>{@link ConcurrentParameterMap}</li>
     *                  <li>{@link ArticleWriter}</li>
     *                  <li>{@link ArticleReader}</li>
     *                  <li>{@link Executor}</li>
     *                  <li>{@link ExecutorService}</li>
     *              </ul>
     * @param <T>   The type of the component.
     * @return An instance of the specified class, or null if creation fails.
     */
    <T> T create(Class<T> clazz);
}
