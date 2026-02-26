package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        // Si no hay error (URL cambia), esperamos la siguiente página
        try {
            waitForUrlContains("checkout-step-two.html");
        } catch (Exception e) {
            // Si hay error, nos quedamos en la misma página (test de campos obligatorios)
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
