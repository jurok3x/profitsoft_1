package com.ykotsiuba.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSystemUtils {

    private static final String EXTENSION = ".json";

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
}
