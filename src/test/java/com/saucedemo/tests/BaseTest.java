package com.saucedemo.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Esta es la Clase Base de todos los Tests.
 * Se encarga de abrir y cerrar el navegador.
 */
public class BaseTest {
    // 1. El navegador que usaremos en los tests
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // 2. WebDriverManager configura el chromedriver.exe automáticamente
        WebDriverManager.chromedriver().setup();
        
        // 3. Iniciamos el navegador Chrome
        driver = new ChromeDriver();
        
        // 4. Configuraciones básicas de estabilidad
        driver.manage().window().maximize(); // Abre la ventana completa
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Espera a que los elementos carguen
        
        // 5. Vamos a la página de Sauce Demo
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        // 6. Cerramos el navegador al terminar CADA test
        if (driver != null) {
            driver.quit();
        }
    }
}
