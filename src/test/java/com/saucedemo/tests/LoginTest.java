package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Suite de pruebas para el Login.
 * Hereda de BaseTest para tener el motor del navegador.
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(driver);
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][]{
            {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
            {"non_existent_user", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
            {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
            {"", "secret_sauce", "Epic sadface: Username is required"},
            {"standard_user", "", "Epic sadface: Password is required"}
        };
    }

    @Test(priority = 1, description = "Login exitoso con usuario estándar")
    public void testSuccessfulLogin() {
        String title = loginPage.loginAs("standard_user", "secret_sauce")
                                .getTitle();
        
        Assert.assertEquals(title, "Products", "El título de la página de inventario es incorrecto.");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), 
            "El login con usuario estándar no redirigió a la página de inventario.");
    }

    @Test(dataProvider = "invalidLoginData", priority = 2, description = "Intentos de login con credenciales inválidas")
    public void testInvalidLoginAttempts(String username, String password, String expectedError) {
        String actualError = loginPage.loginWithInvalidCredentials(username, password)
                                     .getErrorMessage();
        
        Assert.assertEquals(actualError, expectedError, 
            "El mensaje de error no es el esperado para el usuario: " + username);
    }
}
