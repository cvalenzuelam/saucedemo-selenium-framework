package com.saucedemo.base;

import org.openqa.selenium.WebDriver;

public class BasePage {
    // 1. Declaramos el WebDriver como 'protected'
    protected WebDriver driver;

    // 2. El Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
