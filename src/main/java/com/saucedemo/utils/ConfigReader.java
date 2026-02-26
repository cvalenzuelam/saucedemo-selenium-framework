package com.saucedemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            // Ruta relativa al archivo de configuración
            String path = "src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("[WARN] No se encontró el archivo config.properties, se usarán valores por defecto o de entorno.");
        }
    }

    /**
     * Obtiene el valor de una propiedad. Prioridad: Sistema > Entorno > config.properties.
     * @param key Clave de la propiedad.
     * @return Valor de la propiedad.
     */
    public static String getProperty(String key) {
        // 1. Prioridad: Propiedades del sistema (mvn -Dkey=value)
        String value = System.getProperty(key);
        if (value != null) return value;

        // 2. Prioridad: Variables de entorno (export KEY=value)
        value = System.getenv(key.toUpperCase().replace(".", "_"));
        if (value != null) return value;

        // 3. Prioridad: Archivo config.properties
        return properties.getProperty(key);
    }

    // Métodos de conveniencia para las propiedades más comunes con valores por defecto
    public static String getBrowser() { 
        String val = getProperty("browser");
        return val != null ? val : "chrome"; 
    }

    public static String getUrl() { 
        String val = getProperty("url");
        return val != null ? val : "https://www.saucedemo.com/"; 
    }

    public static int getTimeout() { 
        String val = getProperty("timeout");
        try {
            return val != null ? Integer.parseInt(val) : 10;
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    public static boolean isHeadless() { 
        String val = getProperty("headless");
        return val != null ? Boolean.parseBoolean(val) : true;
    }
}
