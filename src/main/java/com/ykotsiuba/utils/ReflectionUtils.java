package com.ykotsiuba.utils;

import com.ykotsiuba.entity.Article;

import java.lang.reflect.Field;
import java.util.Arrays;

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

    public static boolean contains (String fieldName) {
        Field[] fields = Article.class.getDeclaredFields();
        return Arrays.stream(fields).anyMatch(field -> field.getName().equals(fieldName));
    }
}
