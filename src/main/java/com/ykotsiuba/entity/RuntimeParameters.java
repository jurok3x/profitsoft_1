package com.ykotsiuba.entity;

import com.ykotsiuba.utils.FileSystemUtils;
import com.ykotsiuba.utils.ReflectionUtils;
import lombok.Getter;

@Getter
public class RuntimeParameters {

    private static final int REQUIRED_ARGS_COUNT = 2;

    private String folderPath;

    private String parameterName;

    public RuntimeParameters(String[] args) {
        validateArgs(args);
        this.folderPath = args[0];
        this.parameterName = args[1];
    }

    private boolean validateArgs(String[] args) {
        if (args == null || args.length < REQUIRED_ARGS_COUNT) {
            throw new IllegalArgumentException("Runtime parameters not provided or insufficient.");
        }
        if(!validatePath(args)) {
            throw new IllegalArgumentException("Path is not correct.");
        }
        if(!validateParameter(args)) {
            throw new IllegalArgumentException("Parameter is not correct.");
        }
        return true;
    }

    private boolean validatePath(String[] args) {
        if (args[0] == null) {
            return false;
        }
        String path = args[0];
        return FileSystemUtils.isValidPath(path);
    }

    private boolean validateParameter(String[] args) {
        if (args[1] == null) {
            return false;
        }
        String parameter = args[1];
        return ReflectionUtils.contains(parameter);
    }
}
