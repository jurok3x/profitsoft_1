package com.ykotsiuba.utils;

import com.ykotsiuba.entity.Article;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static Object getFieldValue (Article article, String fieldName) {
        try {
            Field field = Article.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(article);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
