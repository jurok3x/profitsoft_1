package com.ykotsiuba.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentParameterMap {

    private final ConcurrentMap<String, Integer> parameters;

    public ConcurrentParameterMap() {
        this.parameters = new ConcurrentHashMap<>();
    }

    public void add(String parameter) {
        parameters.compute(parameter, (key, value) -> (value == null) ? 1 : value + 1);
    }

    public Map<String, Integer> getStatistics() {
        return parameters;
    }
}
