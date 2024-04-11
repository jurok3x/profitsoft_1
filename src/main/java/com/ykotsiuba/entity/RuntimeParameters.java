package com.ykotsiuba.entity;

import lombok.Getter;

@Getter
public class RuntimeParameters {

    private static final int REQUIRED_ARGS_COUNT = 2;

    private String folderPath;

    private String parameterName;

    public RuntimeParameters(String[] args) {
        if (args == null || args.length < REQUIRED_ARGS_COUNT || args[0] == null || args[1] == null) {
            throw new RuntimeException("Runtime parameters not provided or insufficient.");
        }
        this.folderPath = args[0];
        this.parameterName = args[1];
    }
}
