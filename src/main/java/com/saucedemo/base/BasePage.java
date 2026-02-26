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
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            // Intentamos click nativo primero (más real)
            element.click();
        } catch (Exception e) {
            // Fallback a JavaScript si el nativo falla (ej. por scroll o intercepción)
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
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
