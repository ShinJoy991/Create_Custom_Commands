package com.github.shinjoy991.createcustomcommand.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.shinjoy991.createcustomcommand.CreateCustomCommand.LOGGER;
public class CreateJson {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    public static Path configPath = FMLPaths.CONFIGDIR.get().resolve("createcustomcommands");
    public static Path configFile = configPath.resolve("createcustomcommands_config.json");
    public static void CreateJsonConfigFile() {

        // Check if the configfolder directory exists
        if (!Files.exists(configPath)) {
            // If it doesn't exist, create the directory
            try {
                Files.createDirectories(configPath);
            } catch (IOException e) {
                LOGGER.error("Failed to create directory: {}", configPath, e);
                return;
            }
        }

        // Check if the config file already exists
        if (Files.exists(configFile)) {
            LOGGER.info("Config file already exists, skipping creation: {}", configFile);
            return;
        }

        // Create the JSON structure
        Map<String, Object> jsonData = new LinkedHashMap<>(); // Using LinkedHashMap to maintain insertion order

        // Add comments
        List<String> comments = new ArrayList<>();
        comments.add("This is config section for CreateCustomCommand mod");
        comments.add("The key is the alias for each command, change it to match your desire");
        comments.add("Second line is permission for player who type this comamnd");
        comments.add("Third line is notify, it's command feedback");
        comments.add("You can add more commands in each key section, as long as it start with " +
                "'commandx', the x is the number");
        jsonData.put("__comment", comments);

        // Add data for 'key1' section
        Map<String, Object> data1 = new LinkedHashMap<>(); // Using LinkedHashMap to maintain insertion order
        data1.put("permission", "4");
        data1.put("notify", "yes");
        data1.put("command1", "say Command key1 activated");
        data1.put("command2", "say Command key1 command2");
        data1.put("command3", "say Command key1 command3");
        data1.put("command4", "say Command key1 command4");
        jsonData.put("key1", data1);

        // Add data for 'g0' section
        Map<String, Object> data2 = new LinkedHashMap<>(); // Using LinkedHashMap to maintain insertion order
        data2.put("permission", "4");
        data2.put("notify", "yes");
        data2.put("command1", "gamemode survival");
        jsonData.put("g0", data2);

        Map<String, Object> data3 = new LinkedHashMap<>();
        data3.put("permission", "4");
        data3.put("notify", "no");
        data3.put("command1", "gamemode creative");
        jsonData.put("g1", data3);

        Map<String, Object> data4 = new LinkedHashMap<>();
        data4.put("permission", "4");
        data4.put("notify", "no");
        data4.put("command1", "gamemode adventure");
        jsonData.put("g2", data4);

        Map<String, Object> data5 = new LinkedHashMap<>();
        data5.put("permission", "4");
        data5.put("notify", "no");
        data5.put("command1", "gamemode spectator");
        jsonData.put("g3", data5);

        Map<String, Object> data6 = new LinkedHashMap<>();
        data6.put("permission", "4");
        data6.put("notify", "no");
        data6.put("command1", "difficulty peaceful");
        jsonData.put("d0", data6);

        Map<String, Object> data7 = new LinkedHashMap<>();
        data7.put("permission", "4");
        data7.put("notify", "yes");
        data7.put("command1", "difficulty easy");
        jsonData.put("d1", data7);

        Map<String, Object> data8 = new LinkedHashMap<>();
        data8.put("permission", "4");
        data8.put("notify", "yes");
        data8.put("command1", "difficulty normal");
        jsonData.put("d2", data8);

        Map<String, Object> data9 = new LinkedHashMap<>();
        data9.put("permission", "4");
        data9.put("notify", "yes");
        data9.put("command1", "difficulty hard");
        jsonData.put("d3", data9);

        Map<String, Object> data10 = new LinkedHashMap<>();
        data10.put("permission", "4");
        data10.put("notify", "yes");
        data10.put("command1", "time set day");
        jsonData.put("d", data10);

        Map<String, Object> data11 = new LinkedHashMap<>();
        data11.put("permission", "4");
        data11.put("notify", "yes");
        data11.put("command1", "time set night");
        jsonData.put("n", data11);

        Map<String, Object> data12 = new LinkedHashMap<>();
        data12.put("permission", "4");
        data12.put("notify", "yes");
        data12.put("command1", "kill @e[type=!player]");
        jsonData.put("k", data12);

        Map<String, Object> data13 = new LinkedHashMap<>();
        data13.put("permission", "4");
        data13.put("notify", "no");
        data13.put("command1", "kill @e[type=item]");
        jsonData.put("ki", data13);

        Map<String, Object> data14 = new LinkedHashMap<>();
        data14.put("permission", "4");
        data14.put("notify", "no");
        data14.put("command1", "kill @e[type=!player,type=!item]");
        jsonData.put("km", data14);

        Map<String, Object> data15 = new LinkedHashMap<>();
        data15.put("permission", "4");
        data15.put("notify", "yes");
        data15.put("command1", "weather clear");
        jsonData.put("wea", data15);

        // Write the data to the JSON file
        try (FileWriter writer = new FileWriter(configFile.toFile())) {
            // Use GSON to serialize the data to JSON
            GSON.toJson(jsonData, writer);
        } catch (IOException exception) {
            LOGGER.error("Failed to write config file: {}", configFile, exception);
        }
    }
}
