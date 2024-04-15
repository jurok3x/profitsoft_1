package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;

@JacksonXmlRootElement(localName = "item")
@Builder
public class StatisticsItem {
    @JacksonXmlElementWrapper(localName = "value")
    private String value;
    @JacksonXmlElementWrapper(localName = "count")
    private Integer count;
}
