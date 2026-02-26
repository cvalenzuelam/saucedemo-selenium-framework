package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setupCart() {
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);

        // Flujo inicial para llegar al carrito usando Fluent Interface
        inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();
    }

    @Test(description = "Validar que el producto en el carrito es el correcto")
    public void testProductInCart() {
        // En SauceDemo, el primer producto por defecto es "Sauce Labs Backpack"
        Assert.assertEquals(cartPage.getFirstItemName(), "Sauce Labs Backpack", 
            "El nombre del producto en el carrito no coincide.");
    }

    @Test(description = "Validar que hay un elemento en la lista del carrito")
    public void testCartItemCount() {
        Assert.assertEquals(cartPage.getCartItemsCount(), 1, 
            "Debería haber exactamente 1 producto en el carrito.");
    }

    @Test(description = "Validar que se puede remover un producto desde el carrito")
    public void testRemoveFromCart() {
        cartPage.removeFirstItem();
        Assert.assertEquals(cartPage.getCartItemsCount(), 0, 
            "El producto no se eliminó de la lista del carrito.");
    }

    @Test(description = "Validar que el botón Continue Shopping regresa al inventario")
    public void testContinueShopping() {
        cartPage.clickContinueShopping();
        // Espera explícita para asegurar que la navegación terminó en headless
        org.openqa.selenium.support.ui.WebDriverWait longWait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        longWait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("inventory.html"));
        
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), 
            "No se regresó a la página de inventario.");
    }
}
