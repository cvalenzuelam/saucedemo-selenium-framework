package com.saucedemo.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(locator));
        org.openqa.selenium.WebElement element = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    // SafeNavigation con reintentos para CI
    protected void safeNavigate(By locator, String expectedUrlPart) {
        click(locator);
        try {
            // Intento de espera corto para reintentar si falla
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(expectedUrlPart));
        } catch (Exception e) {
            // Si no navegó, forzamos click por JS y esperamos el tiempo completo
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
            waitForUrlContains(expectedUrlPart);
        }
    }

    protected void waitForUrlContains(String urlPart) {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(urlPart));
    }
}
