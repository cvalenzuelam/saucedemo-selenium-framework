package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        // Usamos presenceOfAllElements para que espere al menos a que la lista cargue
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems)).size();
        } catch (Exception e) {
            return 0; // Si no hay elementos, la lista no existe
        }
    }

    public String getFirstItemName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemName)).getText();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void removeFirstItem() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButtons)).click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
    }
}
