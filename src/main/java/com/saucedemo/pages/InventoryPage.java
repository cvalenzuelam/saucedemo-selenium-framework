package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {

    // 1. Localizadores
    private By titleText = By.className("title");
    private By inventoryItems = By.className("inventory_item");
    private By cartLink = By.className("shopping_cart_link");
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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(titleText)).getText();
    }

    public int getInventoryItemsCount() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(inventoryItems)).size();
    }

    public void addFirstItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons)).click();
    }

    public void removeFirstItemFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButtons)).click();
    }

    public String getCartBadgeCount() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void selectSortOption(String optionText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown)).sendKeys(optionText);
    }

    public String getFirstItemPrice() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemPrices)).get(0).getText();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }
}
