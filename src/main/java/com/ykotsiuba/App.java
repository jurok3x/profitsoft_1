package com.ykotsiuba;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.Executor;
import com.ykotsiuba.service.impl.ArticleReaderImpl;
import com.ykotsiuba.service.impl.ArticleWriterImpl;
import com.ykotsiuba.service.impl.DefaultArticleComponentsFactory;
import com.ykotsiuba.service.impl.ExecutorImpl;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ArticleComponentsFactory factory = new DefaultArticleComponentsFactory();
        Executor service = factory.createExecutorService(args);
        service.run();
    }
}
