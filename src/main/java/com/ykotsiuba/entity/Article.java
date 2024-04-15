package com.ykotsiuba.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonAutoDetect
public class Article {

    private String title;

    private String author;

    private String journal;

    private String field;

    private Integer year;
}
