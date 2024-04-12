package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@JacksonXmlRootElement(localName = "statistics")
public class ArticleStatistics {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    private List<StatisticsItem> statistics;

    public ArticleStatistics() {
        this.statistics = new ArrayList<>();
    }

    public void add(StatisticsItem item) {
        statistics.add(item);
    }
}
