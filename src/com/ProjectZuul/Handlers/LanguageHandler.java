package com.ProjectZuul.Handlers;


import com.ProjectZuul.Enums.Language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the selected language by selecting the .txt file corresponding to the language value.
 *
 * @author Richard Ringia
 */
public class LanguageHandler {

    /**
     * Keys and values of all, keys are always the same, their values depend on the selected language.
     */
    private Map<String, String> translations;

    /**
     * Created a language handler and gives translations its values depending on the given language.
     *
     * @param language the selected language by the player.
     */
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

    /**
     * Get string.
     *
     * @param key The key of the hashmap which obtains a value depending on the language. Keys can be found in languages folder alongside their value.
     * @return The string of the given key in the current language.
     */
    public String get(String key) {
        String value = this.translations.get(key);
        return value != null ? value : key;
    }

    /**
     *
     * @param language the selected language by the player.
     * @return a String of the selected language so we can use it in the pathname to languages folder, depending on the language it should select a different .txt file.
     */
    private String getLanguage(Language language) {
        switch (language){
            case EN:
                return "en";
            default:
                return "nl";
        }
    }
}
