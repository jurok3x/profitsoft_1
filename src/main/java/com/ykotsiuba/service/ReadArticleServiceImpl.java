package com.ykotsiuba.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadArticleServiceImpl {

    private final String INPUT_NAME = "input.txt";

    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_NAME))) {
            String jsonObject = readJsonObject(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readJsonObject(BufferedReader reader) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        boolean inObject = false; // Flag to track whether we are inside a JSON object
        int braceCount = 0; // Counter for open and closing braces

        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            char charRead = (char) nextChar;

            // Append character if inside JSON object
            if (inObject) {
                jsonBuilder.append(charRead);
                if (charRead == '{') {
                    braceCount++;
                } else if (charRead == '}') {
                    braceCount--;
                    // If braceCount becomes 0, we have reached the end of the JSON object
                    if (braceCount == 0) {
                        break;
                    }
                }
            } else if (charRead == '{') {
                inObject = true;
                jsonBuilder.append(charRead);
                braceCount++;
            }
        }

        return jsonBuilder.toString();
    }
}
