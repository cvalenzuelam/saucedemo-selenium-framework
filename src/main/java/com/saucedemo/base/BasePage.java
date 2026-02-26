package com.saucedemo.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Clase base para todas las páginas del proyecto SauceDemo.
 * Contiene interacciones robustas mediante JavaScript y esperas explícitas para CI.
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Aumentamos timeout base
    }

    protected void click(By locator) {
        // 1. Esperamos a que sea cliqueable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        // 2. Scroll para centrar el elemento (vital en Headless)
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        
        // 3. Pequeña espera por si hay alguna animación de scroll o reactividad
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        // 4. Click por JavaScript con triple disparo para asegurar el evento de React
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].click(); " +
                "arguments[0].dispatchEvent(new MouseEvent('mousedown', {bubbles: true})); " +
                "arguments[0].dispatchEvent(new MouseEvent('mouseup', {bubbles: true}));", element);
        } catch (Exception e) {
            // Si falla el JS, intentamos el click nativo
            element.click();
        }
        
        // 5. Pausa técnica mínima post-click
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    protected void safeNavigate(By locator, String expectedUrlPart) {
        waitForPageReady();
        click(locator);
        // Esperamos con reintento si no navegó a la primera
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(expectedUrlPart));
        } catch (Exception e) {
            // Reintento manual via click por JS si falló la transición inicial
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
            waitForUrlContains(expectedUrlPart);
        }
    }

    protected void waitForPageReady() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> 
            ((org.openqa.selenium.JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    protected void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }
}
