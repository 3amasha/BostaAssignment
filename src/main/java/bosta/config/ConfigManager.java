package bosta.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    private ConfigManager() {
        // Prevent instantiation
    }

    // Load the properties file only once at startup
    static {
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
        }
    }

    /* ==============================
     * Environment URLs
     * ============================== */

    public static String getBaseUrl() {
        return getProperty("base.url");
    }
    /* ==============================
     * Timeout settings (in milliseconds)
     * ============================== */

    public static int getConnectionTimeout() {
        return Integer.parseInt(getProperty("connection.timeout"));
    }

    public static int getReadTimeout() {
        return Integer.parseInt(getProperty("read.timeout"));
    }

    public static long getMaxResponseTimeout() {
        return Long.parseLong(getProperty("max.response.timeout"));
    }

    /* ==============================
     * Logging toggles
     * ============================== */
    public static boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("request.logging.enabled"));
    }

    public static boolean isResponseLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("response.logging.enabled"));
    }

    /* ==============================
     * Authentication / Tokens
     * ============================== */
    public static String getPickupToken() {
        return getProperty("pickup.token");
    }

    public static String getDeviceId() {
        return getProperty("device.id");
    }

    public static String getDeviceFingerprint() {
        return getProperty("device.fingerprint");
    }


    /* ==============================
     * Helper Method
     * ============================== */
    private static String getProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing configuration key: " + key);
        }
        return value.trim();
    }
}
