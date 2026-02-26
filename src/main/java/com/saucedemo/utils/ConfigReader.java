package com.saucedemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Ruta relativa al archivo de configuración
            String path = "src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo cargar el archivo config.properties: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene el valor de una propiedad dada su clave.
     * @param key Clave de la propiedad.
     * @return Valor de la propiedad.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Métodos de conveniencia para las propiedades más comunes
    public static String getBrowser() { return getProperty("browser"); }
    public static String getUrl() { return getProperty("url"); }
    public static int getTimeout() { return Integer.parseInt(getProperty("timeout")); }
    public static boolean isHeadless() { return Boolean.parseBoolean(getProperty("headless")); }
}
