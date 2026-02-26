package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa la página de Login de Sauce Demo.
 * Hereda de BasePage para usar su driver.
 */
public class LoginPage extends BasePage {

    // 1. LOCALIZADORES (Los elementos que Selenium buscará)
    // Usamos 'private' porque solo esta clase debe saber cómo se llaman estos IDs.
    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorText = By.cssSelector("h3[data-test='error']");

    // 2. EL CONSTRUCTOR (El puente con el driver)
    public LoginPage(WebDriver driver) {
        // Llamamos al constructor de BasePage (super) para que guarde el driver.
        super(driver);
    }

    // 3. ACCIONES (Los métodos que los tests llamarán)

    public LoginPage enterUsername(String user) {
        driver.findElement(usernameInput).sendKeys(user);
        return this;
    }

    public LoginPage enterPassword(String pass) {
        driver.findElement(passwordInput).sendKeys(pass);
        return this;
    }

    public InventoryPage clickLogin() {
        driver.findElement(loginButton).click();
        return new InventoryPage(driver);
    }

    /**
     * Escribe el usuario y contraseña y hace click en el botón.
     * @return Una nueva instancia de InventoryPage tras un login exitoso.
     */
    public InventoryPage loginAs(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        return clickLogin();
    }

    /**
     * Realiza un intento de login que se espera que falle.
     * @return La misma instancia de LoginPage para verificar el error.
     */
    public LoginPage loginWithInvalidCredentials(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        driver.findElement(loginButton).click();
        return this;
    }

    /**
     * Obtiene el texto del mensaje de error si el login falla.
     */
    public String getErrorMessage() {
        return driver.findElement(errorText).getText();
    }
}
