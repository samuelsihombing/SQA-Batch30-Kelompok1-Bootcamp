package com.hadir.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException ex) {
            // Fallback load via classloader jika dijalankan dari JAR / classpath target
            try (InputStream cpInput = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (cpInput != null) {
                    properties.load(cpInput);
                } else {
                    System.err.println("Peringatan: File config.properties tidak ditemukan pada path: " + CONFIG_FILE_PATH);
                }
            } catch (IOException e) {
                System.err.println("Error saat membaca konfigurasi dari classpath: " + e.getMessage());
            }
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(key, "");
    }

    public static String getLoginUrl() {
        return getProperty("login.url");
    }

    public static String getLoginEmail() {
        return getProperty("login.email");
    }

    public static String getLoginPassword() {
        return getProperty("login.password");
    }
}
