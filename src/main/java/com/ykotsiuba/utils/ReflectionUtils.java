package com.ykotsiuba.utils;

import com.ykotsiuba.entity.Article;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * The ReflectionUtils class provides utility methods for reflection operations.
 */
public class ReflectionUtils {

    /**
     * Retrieves the value of the specified field from the given Article object using reflection.
     *
     * @param article   The Article object from which to retrieve the field value.
     * @param fieldName The name of the field whose value to retrieve.
     * @return The value of the specified field in the Article object, or null if the field is not found or accessible.
     */
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

    /**
     * Checks if the Article class contains a field with the specified name.
     *
     * @param fieldName The name of the field to check for.
     * @return true if the Article class contains a field with the specified name, false otherwise.
     */
    public static boolean contains (String fieldName) {
        Field[] fields = Article.class.getDeclaredFields();
        return Arrays.stream(fields).anyMatch(field -> field.getName().equals(fieldName));
    }
}
