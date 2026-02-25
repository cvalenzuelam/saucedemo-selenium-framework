package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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

    @Test(priority = 1, description = "Login exitoso con usuario estándar")
    public void testSuccessfulLogin() {
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), 
            "No se redirigió a la página de inventario tras el login exitoso.");
    }

    @Test(priority = 2, description = "Error al intentar entrar con usuario bloqueado")
    public void testLockedOutUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "Epic sadface: Sorry, this user has been locked out.", 
            "El mensaje para usuario bloqueado es incorrecto.");
    }

    @Test(priority = 3, description = "Error con usuario que no existe")
    public void testInvalidUser() {
        loginPage.login("non_existent_user", "secret_sauce");
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "Epic sadface: Username and password do not match any user in this service", 
            "El mensaje para usuario inexistente es incorrecto.");
    }

    @Test(priority = 4, description = "Error con contraseña incorrecta")
    public void testWrongPassword() {
        loginPage.login("standard_user", "wrong_password");
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "Epic sadface: Username and password do not match any user in this service", 
            "El mensaje para contraseña incorrecta es incorrecto.");
    }

    @Test(priority = 5, description = "Error cuando el nombre de usuario está vacío")
    public void testEmptyUsername() {
        loginPage.login("", "secret_sauce");
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "Epic sadface: Username is required", 
            "Debería indicar que el usuario es requerido.");
    }

    @Test(priority = 6, description = "Error cuando el password está vacío")
    public void testEmptyPassword() {
        loginPage.login("standard_user", "");
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "Epic sadface: Password is required", 
            "Debería indicar que el password es requerido.");
    }
}
