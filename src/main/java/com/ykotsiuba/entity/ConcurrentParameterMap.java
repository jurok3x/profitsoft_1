package com.ykotsiuba.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@XmlRootElement
public class ConcurrentParameterMap {

    private final ConcurrentMap<String, Integer> parameters;

    public ConcurrentParameterMap() {
        this.parameters = new ConcurrentHashMap<>();
    }

    public void add(String parameter) {
        parameters.compute(parameter, (key, value) -> (value == null) ? 1 : value + 1);
    }

    @XmlElement
    public Map<String, Integer> getStatistics() {
        return parameters;
    }
}
