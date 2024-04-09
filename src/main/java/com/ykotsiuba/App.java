package com.ykotsiuba;

import com.ykotsiuba.service.ArticleProducer;
import com.ykotsiuba.service.ParallelExecutorService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ParallelExecutorService service = new ParallelExecutorService();
        service.run();
    }
}
