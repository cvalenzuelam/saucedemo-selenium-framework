package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Representa la página de Login de Sauce Demo.
 * Hereda de BasePage para usar su driver.
 */
public class LoginPage extends BasePage {

    // 1. LOCALIZADORES
    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorText = By.cssSelector("h3[data-test='error']");

    // 2. EL CONSTRUCTOR
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 3. ACCIONES

    public LoginPage enterUsername(String user) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(user);
        return this;
    }

    public LoginPage enterPassword(String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(pass);
        return this;
    }

    public InventoryPage clickLogin() {
        click(loginButton);
        return new InventoryPage(driver);
    }

    /**
     * Escribe el usuario y contraseña y hace click en el botón.
     */
    public InventoryPage loginAs(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        return clickLogin();
    }

    /**
     * Realiza un intento de login que se espera que falle.
     */
    public LoginPage loginWithInvalidCredentials(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        click(loginButton);
        return this;
    }

    /**
     * Obtiene el texto del mensaje de error si el login falla.
     */
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorText)).getText();
    }
}
