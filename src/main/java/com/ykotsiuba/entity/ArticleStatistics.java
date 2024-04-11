package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class ArticleStatistics {

    @JacksonXmlElementWrapper(localName = "statistics")
    @JacksonXmlProperty(localName = "item")
    private List<StatisticsItem> statistics;

    public ArticleStatistics() {
        this.statistics = new ArrayList<>();
    }

    public void add(StatisticsItem item) {
        statistics.add(item);
    }
}
