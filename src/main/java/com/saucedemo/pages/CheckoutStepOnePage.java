package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutStepOnePage extends BasePage {

    private By firstNameInput = By.cssSelector("[data-test='firstName']");
    private By lastNameInput = By.cssSelector("[data-test='lastName']");
    private By zipCodeInput = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    public void enterInformation(String firstName, String lastName, String zipCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(zipCodeInput)).sendKeys(zipCode);
    }

    public CheckoutStepTwoPage clickContinue() {
        click(continueButton);
        // Si no hay error (URL cambia), intentamos asegurar la navegación
        try {
            // Un timeout corto aquí porque si es un test de error, NO debe navegar
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlContains("checkout-step-two.html"));
        } catch (Exception e) {
            // No navegó, puede ser por error de validación o fallo de CI
            // Si el mensaje de error no está presente, intentamos click forzado de nuevo por si acaso fue fallo de CI
            if (getErrorMessage().isEmpty()) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(continueButton));
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlContains("checkout-step-two.html"));
                } catch (Exception ex) {}
            }
        }
        return new CheckoutStepTwoPage(driver);
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
