package com.ykotsiuba;

import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.service.ArticleProducer;
import com.ykotsiuba.service.ParallelExecutorService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        RuntimeParameters parameters = new RuntimeParameters(args);
        ParallelExecutorService service = new ParallelExecutorService(parameters);
        service.run();
    }
}
