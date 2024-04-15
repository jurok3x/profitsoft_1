package com.ykotsiuba.service;

public interface ArticleComponentsFactory {
    <T> T create(Class<T> clazz);
}
