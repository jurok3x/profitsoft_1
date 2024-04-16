package com.ykotsiuba.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RuntimeParametersTest {

    private static final String PATH = "C:\\";

    private static final String NAME = "year";

    @Test
    void whenParametersProvidedThenReturnObject() {
        String[] args = new String[]{PATH, NAME};
        RuntimeParameters parameters = new RuntimeParameters(args);
        assertNotNull(parameters);
    }

    @Test
    void whenParametersNotProvidedThenThrowsException() {
        String[] args = null;
        assertThrows(IllegalArgumentException.class, () -> new RuntimeParameters(args));
    }

    @Test
    void whenParametersNullThenThrowsException() {
        String[] args = new String[]{null, null};
        assertThrows(IllegalArgumentException.class, () -> new RuntimeParameters(args));
    }

    @Test
    void whenPathInvalidThenThrowsException() {
        String[] args = new String[]{"not_path", NAME};
        assertThrows(IllegalArgumentException.class, () -> new RuntimeParameters(args));
    }

    @Test
    void whenParameterNameInvalidThenThrowsException() {
        String[] args = new String[]{PATH, "invalid"};
        assertThrows(IllegalArgumentException.class, () -> new RuntimeParameters(args));
    }
}