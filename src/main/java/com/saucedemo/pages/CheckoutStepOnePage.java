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
        // SauceDemo a veces tarda en CI, esperamos hasta 10 segundos la navegación
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> 
                d.getCurrentUrl().contains("checkout-step-two.html")
            );
        } catch (Exception e) {
            // Si no navegó, puede ser por un error de validación; permitimos que el test decida con su aserción
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
