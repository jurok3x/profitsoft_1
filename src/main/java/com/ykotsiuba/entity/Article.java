package com.ykotsiuba.entity;

import lombok.Data;

@Data
public class Article {
    private String title;

    private String author;

    private String journal;

    private String field;

    private Integer year;

}
