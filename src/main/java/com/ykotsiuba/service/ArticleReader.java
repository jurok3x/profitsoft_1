package com.ykotsiuba.service;

import java.util.List;

public interface ArticleReader {
    /**
     * Reads article data from a specific source
     * and return a list of tasks (Runnables) to process the data asynchronously.
     *
     * @return A list of Runnables representing tasks to process the read article data.
     */
    List<Runnable> read();
}
