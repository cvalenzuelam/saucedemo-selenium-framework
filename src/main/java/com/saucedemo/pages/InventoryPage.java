package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    // 1. Localizadores
    private By titleText = By.className("title");
    private By inventoryItems = By.className("inventory_item");
    private By cartBadge = By.className("shopping_cart_badge");
    private By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private By removeButtons = By.cssSelector("button[id^='remove']");
    private By sortDropdown = By.className("product_sort_container");
    private By itemPrices = By.className("inventory_item_price");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    // 2. Acciones
    public String getTitle() {
        return driver.findElement(titleText).getText();
    }

    public int getInventoryItemsCount() {
        return driver.findElements(inventoryItems).size();
    }

    public void addFirstItemToCart() {
        driver.findElements(addToCartButtons).get(0).click();
    }

    public void removeFirstItemFromCart() {
        driver.findElements(removeButtons).get(0).click();
    }

    public String getCartBadgeCount() {
        try {
            return driver.findElement(cartBadge).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void selectSortOption(String optionText) {
        driver.findElement(sortDropdown).sendKeys(optionText);
    }

    public String getFirstItemPrice() {
        return driver.findElements(itemPrices).get(0).getText();
    }

    public void goToCart() {
        driver.findElement(cartBadge).click();
    }
}
