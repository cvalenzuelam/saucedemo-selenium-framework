package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    // 1. Localizadores
    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By itemName = By.className("inventory_item_name");
    private By removeButtons = By.cssSelector("button[id^='remove']");
    private By continueShoppingButton = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
        waitForPageReady();
    }

    // 2. Acciones
    public int getCartItemsCount() {
        // Volvemos a findElements directo porque si la lista está vacía no queremos esperar timeout
        return driver.findElements(cartItems).size();
    }

    public String getFirstItemName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemName)).getText();
    }

    public CheckoutStepOnePage clickCheckout() {
        safeNavigate(checkoutButton, "checkout-step-one.html");
        return new CheckoutStepOnePage(driver);
    }

    public void removeFirstItem() {
        int initialCount = getCartItemsCount();
        if (initialCount > 0) {
            // Re-buscamos el elemento justo antes de click para evitar stale elements
            org.openqa.selenium.WebElement removeBtn = driver.findElement(removeButtons);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", removeBtn);
            
            // Esperamos explícitamente a que el conteo baje
            try {
                wait.until(d -> getCartItemsCount() < initialCount);
            } catch (Exception e) {
                // Reintento final por si hubo lag en el click JS
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(removeButtons));
                wait.until(d -> getCartItemsCount() < initialCount);
            }
        }
    }

    public InventoryPage clickContinueShopping() {
        safeNavigate(continueShoppingButton, "inventory.html");
        return new InventoryPage(driver);
    }
}
