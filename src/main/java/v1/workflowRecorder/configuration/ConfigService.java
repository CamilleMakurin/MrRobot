package v1.workflowRecorder.configuration;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ConfigService {

    private static Map<String, String> config;

    public void loadConfigurationFile(String configPath) throws IOException {
        System.out.println("Loading configuration file in directory: " + configPath);
        Path file = Paths.get(configPath + "\\config.txt");
        BufferedReader bufferedReader = Files.newBufferedReader(file);
        this.config = readConfig(bufferedReader);
        System.out.println("Loaded config:");
        config.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    private Map<String, String> readConfig(BufferedReader bufferedReader) throws IOException {
        Map<String, String> config = new HashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Pair<String, String> pair = ConfigUtil.readValue(line);
            config.put(pair.getKey(), pair.getValue());
        }
        return config;
    }

    public static Optional<String> getConfigValue(String configName) {
        if (config != null && !config.isEmpty()) {
            return Optional.ofNullable(config.get(configName));
        }
        return Optional.empty();
    }
}
