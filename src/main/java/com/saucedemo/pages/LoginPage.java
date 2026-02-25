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
    
    /**
     * Escribe el usuario y contraseña y hace click en el botón.
     */
    public void login(String user, String pass) {
        driver.findElement(usernameInput).sendKeys(user);
        driver.findElement(passwordInput).sendKeys(pass);
        driver.findElement(loginButton).click();
    }

    /**
     * Obtiene el texto del mensaje de error si el login falla.
     */
    public String getErrorMessage() {
        return driver.findElement(errorText).getText();
    }
}
