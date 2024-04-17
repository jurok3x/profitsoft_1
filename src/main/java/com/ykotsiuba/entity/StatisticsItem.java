package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@JacksonXmlRootElement(localName = "item")
@Builder
@Data
@EqualsAndHashCode
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
