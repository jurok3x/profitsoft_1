package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@JacksonXmlRootElement(localName = "statistics")
public class ArticleStatistics {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    private Set<StatisticsItem> statistics;

    public ArticleStatistics() {
        this.statistics = new TreeSet<>();
    }

    public void add(StatisticsItem item) {
        statistics.add(item);
    }
}
