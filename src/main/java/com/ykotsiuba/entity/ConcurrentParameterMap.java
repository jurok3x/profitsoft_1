package com.ykotsiuba.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The ConcurrentParameterMap class provides a concurrent implementation of a parameter map,
 * allowing thread-safe addition and retrieval of parameters along with their counts.
 */
public class ConcurrentParameterMap {

    private final ConcurrentMap<String, Integer> parameters;

    public ConcurrentParameterMap() {
        this.parameters = new ConcurrentHashMap<>();
    }

    /**
     * Adds a parameter to the map, incrementing its count if it already exists.
     *
     * @param parameter The parameter to add.
     */
    public void add(String parameter) {
        parameters.compute(parameter, (key, value) -> (value == null) ? 1 : value + 1);
    }

    /**
     * Retrieves the parameter map containing parameter names and their respective counts.
     *
     * @return The parameter map containing parameter names and their counts.
     */
    public Map<String, Integer> getStatistics() {
        return parameters;
    }
}
