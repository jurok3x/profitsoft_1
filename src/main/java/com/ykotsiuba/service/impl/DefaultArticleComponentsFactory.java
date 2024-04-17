package com.ykotsiuba.service.impl;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The DefaultArticleComponentsFactory class implements the ArticleComponentsFactory interface
 * and provides default implementations for creating various components related to article processing.
 * Uses {@link HashMap} to store all instances.
 */
public class DefaultArticleComponentsFactory implements ArticleComponentsFactory {
    private final RuntimeParameters parameters;
    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();
    private static final String PROPERTY_FILE_PATH = "/app.properties";
    private static final String THREAD_NUMBER_PROPERTY_KEY = "threadNumber";

    private DefaultArticleComponentsFactory(String[] args) {
        parameters = new RuntimeParameters(args);
    }

    public static DefaultArticleComponentsFactory getInstance(String[] args) {
        return new DefaultArticleComponentsFactory(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create (Class<T> clazz) {
        if (clazz == null) {
            return null;
        }

        if(CONTEXT.containsKey(clazz)) {
            return (T) CONTEXT.get(clazz);
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
        if(ExecutorService.class.isAssignableFrom(clazz)) {
            return createExecutorService(clazz);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T createConcurrentParameterMap(Class<T> clazz) {
        ConcurrentParameterMap map = new ConcurrentParameterMap();
        CONTEXT.put(clazz, map);
        return (T) map;
    }

    @SuppressWarnings("unchecked")
    private <T> T createArticleWriter(Class<T> clazz) {
        ConcurrentParameterMap map = create(ConcurrentParameterMap.class);
        ArticleWriter writer = new ArticleWriterImpl(map, parameters);
        CONTEXT.put(clazz, writer);
        return (T) writer;
    }

    @SuppressWarnings("unchecked")
    private <T> T createArticleReader(Class<T> clazz) {
        ConcurrentParameterMap map = create(ConcurrentParameterMap.class);
        ArticleReader reader = new ArticleReaderImpl(map, parameters);
        CONTEXT.put(clazz, reader);
        return (T) reader;
    }

    @SuppressWarnings("unchecked")
    private <T> T createExecutor(Class<T> clazz) {
        ArticleReader reader = create(ArticleReader.class);
        ArticleWriter writer = create(ArticleWriter.class);
        ExecutorService executorService = create(ExecutorService.class);
        Executor executor = new ExecutorImpl(reader, writer, executorService);
        CONTEXT.put(clazz, executor);
        return (T) executor;
    }

    @SuppressWarnings("unchecked")
    private <T> T createExecutorService(Class<T> clazz) {
        int threadNumbers = getThreadNumbers();
        ExecutorService executorService =  Executors.newFixedThreadPool(threadNumbers);
        System.out.printf("Prepared executor with %d threads...%n", threadNumbers);
        CONTEXT.put(clazz, executorService);
        return (T) executorService;
    }

    /**
     * Retrieves the number of threads specified in the properties file.
     *
     * @return The number of threads, defaults to 1 if not specified or if an error occurs.
     */
    private static int getThreadNumbers() {
        Properties properties = new Properties();
        int threadNumbers = 1;
        try (InputStream is = DefaultArticleComponentsFactory.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_PATH)) {
            properties.load(is);
            String threadNumberString = properties.getProperty(THREAD_NUMBER_PROPERTY_KEY);
            if (threadNumberString != null) {
                threadNumbers = Integer.parseInt(threadNumberString);
            }
        } catch (NullPointerException | IOException | NumberFormatException e) {
            System.out.println("Error reading properties file. Running with single thread.");
        }
        return threadNumbers;
    }

}
