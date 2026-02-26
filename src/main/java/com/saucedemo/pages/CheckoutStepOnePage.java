package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutStepOnePage extends BasePage {

    private By firstNameInput = By.cssSelector("#first-name, [data-test='firstName'], [name='firstName']");
    private By lastNameInput = By.cssSelector("#last-name, [data-test='lastName'], [name='lastName']");
    private By zipCodeInput = By.cssSelector("#postal-code, [data-test='postalCode'], [name='postalCode']");
    private By continueButton = By.cssSelector("#continue, [data-test='continue'], [name='continue']");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
        waitForPageReady();
    }

    public void enterInformation(String firstName, String lastName, String zipCode) {
        fillField(firstNameInput, firstName);
        fillField(lastNameInput, lastName);
        fillField(zipCodeInput, zipCode);
    }

    private void fillField(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        element.clear();
        element.sendKeys(value);
        
        // Verificación de seguridad: si sendKeys falló (común en CI/Headless), forzamos vía JS
        String currentValue = element.getAttribute("value");
        if (currentValue == null || currentValue.isEmpty()) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; " +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", 
                element, value);
        }
    }

    public CheckoutStepTwoPage clickContinue() {
        click(continueButton);
        // Esperamos a que la URL cambie para asegurar que el click fue procesado
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("checkout-step-two.html"));
        } catch (Exception ignored) {}
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
