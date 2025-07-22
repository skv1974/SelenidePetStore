package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        String pathToProperties = "src/test/resources/config.properties";

        loadProperties(pathToProperties);
    }

    private void loadProperties(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getBrowser() {
        return properties.getProperty("default.browser");
    }

    public String getResolution() {
        return properties.getProperty("resolution.window");
    }

    public String getAuthToken() {
        return properties.getProperty("auth.token");
    }

}