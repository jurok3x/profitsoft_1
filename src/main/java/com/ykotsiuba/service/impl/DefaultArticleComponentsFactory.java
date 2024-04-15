package com.ykotsiuba.service.impl;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;

import java.util.HashMap;
import java.util.Map;

public class DefaultArticleComponentsFactory implements ArticleComponentsFactory {
    private final RuntimeParameters parameters;
    private final Map<Class<?>, Object> context  = new HashMap<>();
    private static DefaultArticleComponentsFactory instance;

    private DefaultArticleComponentsFactory(String[] args) {
        parameters = new RuntimeParameters(args);
    }

    public static DefaultArticleComponentsFactory getInstance(String[] args) {
        if(instance == null) {
            instance = new DefaultArticleComponentsFactory(args);
        }
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create (Class<T> clazz) {
        if (clazz == null) {
            return null;
        }

        if(context.containsKey(clazz)) {
            return (T) context.get(clazz);
        }

        if(ConcurrentParameterMap.class.isAssignableFrom(clazz)) {
            return createConcurrentParameterMap(clazz);
        }
        if(ArticleWriter.class.isAssignableFrom(clazz)) {
            return createArticleWriter(clazz);
        }
        if(ArticleReader.class.isAssignableFrom(clazz)) {
            return createArticleReader(clazz);
        }
        if(Executor.class.isAssignableFrom(clazz)) {
            return createExecutor(clazz);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T createConcurrentParameterMap(Class<T> clazz) {
        ConcurrentParameterMap map = new ConcurrentParameterMap();
        context.put(clazz, map);
        return (T) map;
    }

    @SuppressWarnings("unchecked")
    private <T> T createArticleWriter(Class<T> clazz) {
        ConcurrentParameterMap map = create(ConcurrentParameterMap.class);
        ArticleWriter writer = new ArticleWriterImpl(map, parameters);
        context.put(clazz, writer);
        return (T) writer;
    }

    @SuppressWarnings("unchecked")
    private <T> T createArticleReader(Class<T> clazz) {
        ConcurrentParameterMap map = create(ConcurrentParameterMap.class);
        ArticleReader reader = new ArticleReaderImpl(map, parameters);
        context.put(clazz, reader);
        return (T) reader;
    }

    @SuppressWarnings("unchecked")
    private <T> T createExecutor(Class<T> clazz) {
        ArticleReader reader = create(ArticleReader.class);
        ArticleWriter writer = create(ArticleWriter.class);
        Executor executor = new ExecutorImpl(reader, writer);
        context.put(clazz, executor);
        return (T) executor;
    }

}
