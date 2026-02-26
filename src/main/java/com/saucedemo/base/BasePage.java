package com.saucedemo.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Clase base para todas las páginas del proyecto SauceDemo.
 * Contiene interacciones robustas mediante JavaScript y esperas explícitas para CI.
 */
public class BasePage {
    // 1. Declaramos el WebDriver como 'protected'
    protected WebDriver driver;
    protected WebDriverWait wait;

    // 2. El Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
        // 1. Esperamos a que sea cliqueable
        org.openqa.selenium.WebElement element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(locator));
        
        // 2. Scroll y visibilidad
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        
        // 3. Click por JavaScript (más estable en CI para SauceDemo)
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        
        // 4. Pausa técnica breve para que la UI de SauceDemo procese el cambio
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    // SafeNavigation con reintentos para CI
    protected void safeNavigate(By locator, String expectedUrlPart) {
        waitForPageReady();
        click(locator);
        // Esperamos un momento razonable para la transición de URL
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(expectedUrlPart));
        } catch (Exception e) {
            // Si el primer intento falló por timeout de Selenium, reintentamos un click JS final
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
            waitForUrlContains(expectedUrlPart);
        }
    }

    protected void waitForPageReady() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> 
            ((org.openqa.selenium.JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    protected void waitForUrlContains(String urlPart) {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(urlPart));
    }
}
