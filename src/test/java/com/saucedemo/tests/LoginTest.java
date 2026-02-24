package com.saucedemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        // Localizadores directos (sin usar Page Object aún para hacerlo rápido)
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Validamos el logo tras iniciar sesión
        WebElement logo = driver.findElement(By.className("app_logo"));
        Assert.assertEquals(logo.getText(), "Swag Labs", "El texto del logo no coincide.");
    }
}