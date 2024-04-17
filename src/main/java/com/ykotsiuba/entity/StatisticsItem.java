package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "item")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsItem implements Comparable<StatisticsItem> {
    @JacksonXmlElementWrapper(localName = "value")
    private String value;
    @JacksonXmlElementWrapper(localName = "count")
    private Integer count;

    @Override
    public int compareTo(StatisticsItem o) {
        return o.getCount() - this.count;
    }
}
