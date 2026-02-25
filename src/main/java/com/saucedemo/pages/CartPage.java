package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    // 1. Localizadores
    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.id("checkout");
    private By itemName = By.className("inventory_item_name");
    private By removeButtons = By.cssSelector("button[id^='remove']");
    private By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // 2. Acciones
    public int getCartItemsCount() {
        return driver.findElements(cartItems).size();
    }

    public String getFirstItemName() {
        return driver.findElement(itemName).getText();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public void removeFirstItem() {
        driver.findElement(removeButtons).click();
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }
}
