package com.ProjectZuul.Handlers;


import com.ProjectZuul.Enums.Language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanguageHandler {

    private String CurrentLanguage = "nl";
    private Map<String, String> translations;

    public LanguageHandler(Language language) {
        this.translations = new HashMap<>();
        StringBuilder path = new StringBuilder();
        path.append("languages/").append(this.getLanguage(language)).append(".txt");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path.toString()));
            String line = reader.readLine();
            while (line != null) {
                String[] strippedLine = line.split(":", 2);
                translations.put(strippedLine[0], strippedLine[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        String value = this.translations.get(key);
        return value != null ? value : key;
    }

    private String getLanguage(Language language) {
        switch (language){
            case DE:
                return "de";
            case EN:
                return "en";
            case FR:
                return "fr";
            default:
                return "nl";
        }
    }
}
