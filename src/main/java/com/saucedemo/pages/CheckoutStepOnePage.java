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
        // Esperamos un momento para ver si hay navegación o error
        try {
            // Un timeout más breve para CI que detecte si hay navegación rápida o error
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> 
                d.getCurrentUrl().contains("checkout-step-two.html") || 
                !getErrorMessage().isEmpty()
            );
        } catch (Exception e) {
            // Fallback: si no hubo navegación y no hay error, intentamos click JS
            if (getErrorMessage().isEmpty() && !driver.getCurrentUrl().contains("checkout-step-two.html")) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(continueButton));
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
