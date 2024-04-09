package com.ykotsiuba.entity;

import java.util.HashMap;
import java.util.Map;

public class ArticleStatistics {

    private Map<String, Integer> statistics;

    public ArticleStatistics() {
        this.statistics = new HashMap<>();
    }

    public void add(String parameter) {
        statistics.compute(parameter, (key, value) -> (value == null) ? 1 : value + 1);
    }

    public Map<String, Integer> getStatistics() {
        return statistics;
    }
}
