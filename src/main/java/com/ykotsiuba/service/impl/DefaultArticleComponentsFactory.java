package com.ykotsiuba.service.impl;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.ArticleReader;
import com.ykotsiuba.service.ArticleWriter;
import com.ykotsiuba.service.Executor;

public class DefaultArticleComponentsFactory implements ArticleComponentsFactory {
    @Override
    public Executor createExecutorService(String[] args) {
        RuntimeParameters parameters = new RuntimeParameters(args);
        ConcurrentParameterMap map = new ConcurrentParameterMap();
        ArticleReader reader = new ArticleReaderImpl(map, parameters);
        ArticleWriter writer = new ArticleWriterImpl(map, parameters);
        return new ExecutorImpl(reader, writer);
    }
}
