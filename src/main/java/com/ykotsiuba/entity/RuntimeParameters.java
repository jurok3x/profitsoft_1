package com.ykotsiuba.entity;

import com.ykotsiuba.utils.FileSystemUtils;
import com.ykotsiuba.utils.ReflectionUtils;
import lombok.Getter;

/**
 * The RuntimeParameters class represents the runtime parameters required for the application.
 * It validates and stores the folder path and parameter name provided as command line arguments.
 */
@Getter
public class RuntimeParameters {

    private static final int REQUIRED_ARGS_COUNT = 2;

    private String folderPath;

    private String parameterName;

    /**
     * Constructs a RuntimeParameters object with the provided command line arguments.
     * Validates the arguments and initializes the folder path and parameter name.
     *
     * @param args The command line arguments containing the folder path and parameter name.
     * @throws IllegalArgumentException If the provided arguments are invalid or insufficient.
     */
    public RuntimeParameters(String[] args) {
        validateArgs(args);
        this.folderPath = args[0];
        this.parameterName = args[1];
    }

    /**
     * Validates the provided command line arguments.
     *
     * @param args The command line arguments to validate.
     * @return true if the arguments are valid, false otherwise.
     * @throws IllegalArgumentException If the provided arguments are invalid or insufficient.
     */
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
