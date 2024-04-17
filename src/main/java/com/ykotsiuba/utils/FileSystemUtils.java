package com.ykotsiuba.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileSystemUtils class provides utility methods for file system operations.
 */
public class FileSystemUtils {

    private static final String EXTENSION = ".json";

    /**
     * Retrieves the list of JSON files in the specified directory path.
     *
     * @param path The directory path.
     * @return A list of absolute paths to JSON files in the directory.
     */
    public static List<String> getJsonFiles(String path) {
        List<String> jsonFiles = new ArrayList<>();

        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(EXTENSION)) {
                        jsonFiles.add(file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.err.println("Directory not found or is not a directory: " + path);
        }

        return jsonFiles;
    }

    /**
     * Checks if the provided path is a valid directory.
     *
     * @param path The directory path to check.
     * @return true if the path exists and is a directory, false otherwise.
     */
    public static boolean isValidPath(String path) {
        Path p = Paths.get(path);
        return Files.exists(p) && Files.isDirectory(p);
    }
}
