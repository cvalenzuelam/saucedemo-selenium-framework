package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class InventoryPage extends BasePage {

    // 1. Localizadores
    private By titleText = By.className("title");
    private By inventoryItems = By.className("inventory_item");
    private By cartLink = By.className("shopping_cart_link");
    private By cartBadge = By.className("shopping_cart_badge");
    private By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private By removeButtons = By.cssSelector("button[id^='remove']");
    private By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private By itemPrices = By.className("inventory_item_price");

    public InventoryPage(WebDriver driver) {
        super(driver);
        waitForPageReady();
    }

    // 2. Acciones
    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(titleText)).getText();
    }

    public int getInventoryItemsCount() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(inventoryItems)).size();
    }

    public void addFirstItemToCart() {
        // En headless, a veces el primer click no se registra por el estado de React.
        // Usamos un bucle de reintento corto basado en la visibilidad del badge.
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons));
                click(addToCartButtons);
                
                // Si el badge aparece, la acción fue exitosa
                new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
                return; 
            } catch (Exception e) {
                attempts++;
            }
        }
        // Fallback final: si el bucle falló, intentamos una última espera larga
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
    }

    public void removeFirstItemFromCart() {
        click(removeButtons);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    public String getCartBadgeCount() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void selectSortOption(String optionText) {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        new Select(selectElement).selectByVisibleText(optionText);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public String getFirstItemPrice() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemPrices)).get(0).getText();
    }

    public void goToCart() {
        safeNavigate(cartLink, "cart.html");
    }
}
