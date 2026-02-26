package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        click(addToCartButtons);
        // Esperamos a que el badge aparezca y sea "1" (o al menos aparezca)
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
    }

    public void removeFirstItemFromCart() {
        click(removeButtons);
        // Esperamos a que el badge desaparezca (SauceDemo lo quita si es 0)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    public String getCartBadgeCount() {
        try {
            // Espera corta para no penalizar si realmente debe ser 0, pero dar tiempo a la UI
            return new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void selectSortOption(String optionText) {
        // En CI es más seguro usar Select y esperar un momento
        org.openqa.selenium.WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        new Select(selectElement).selectByVisibleText(optionText);
        
        // Esperamos a que el primer precio cambie o se estabilice tras el ordenamiento
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public String getFirstItemPrice() {
        // Aseguramos que los elementos estén presentes antes de obtener el primero
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemPrices)).get(0).getText();
    }

    public void goToCart() {
        safeNavigate(cartLink, "cart.html");
    }
}
