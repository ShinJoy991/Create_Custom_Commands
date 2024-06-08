package com.github.shinjoy991.createcustomcommand.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import static com.github.shinjoy991.createcustomcommand.CreateCustomCommand.LOGGER;
public class ReadConfig {
    public static JsonObject jsonObject;
    public static ArrayList<String> keysList = new ArrayList<>();

    public static String readJsonValue(Path configFile) {
        try {
            // Read the contents of the JSON file
            String jsonString = new String(Files.readAllBytes(configFile), StandardCharsets.UTF_8);
            // Convert the JSON string to a JsonObject
            jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

            // Extract keys excluding "__comment"
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String memberName = entry.getKey();
                if (!memberName.equals("__comment")) {
                    // Add the key to keysList
                    keysList.add(memberName);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error when reading Json" + e);
        }

        return null;
    }

    public static ArrayList<String> ReadElement(String key) {
        ArrayList<String> returnArray = new ArrayList<>();
        JsonObject section = jsonObject.getAsJsonObject(key);
        if (section.has("permission")) {
            returnArray.add(section.get("permission").getAsString());
        } else {
            returnArray.add(String.valueOf(4));
        }
        if (section.has("notify")) {
            returnArray.add(section.get("notify").getAsString());
        } else {
            returnArray.add("yes");
        }
        if (section.has("notifystring")) {
            returnArray.add(section.get("notifystring").getAsString());
        } else {
            returnArray.add("notifystringnull");
        }
        for (Map.Entry<String, JsonElement> entry : section.entrySet()) {
            String keyName = entry.getKey();
            if (keyName.startsWith("command")) {
                returnArray.add(section.get(keyName).getAsString());
            }
        }
        while (returnArray.size() < 4) {
            returnArray.add("createcustomcommandnull");
        }
        return returnArray;
    }
}