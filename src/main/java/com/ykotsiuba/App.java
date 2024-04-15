package com.ykotsiuba;

import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.Executor;
import com.ykotsiuba.service.impl.DefaultArticleComponentsFactory;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ArticleComponentsFactory factory = DefaultArticleComponentsFactory.getInstance(args);
        Executor service = factory.create(Executor.class);
        service.run();
    }
}
