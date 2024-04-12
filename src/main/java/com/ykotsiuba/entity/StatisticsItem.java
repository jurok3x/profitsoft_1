package com.ykotsiuba.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;

@JacksonXmlRootElement(localName = "library")
@Builder
public class StatisticsItem {
    private String value;
    private Integer count;
}
