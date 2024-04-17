package com.ykotsiuba;

import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.Executor;
import com.ykotsiuba.service.impl.DefaultArticleComponentsFactory;

/**
 * The App class serves as the entry point to the application.
 * It initializes the article processing components using the provided command line arguments
 * and executes the article processing tasks.
 * @author Yurii Kotsiuba
 */
public class App {
    public static void main( String[] args ) {
        ArticleComponentsFactory factory = DefaultArticleComponentsFactory.getInstance(args);
        Executor service = factory.create(Executor.class);
        service.run();
    }
}
