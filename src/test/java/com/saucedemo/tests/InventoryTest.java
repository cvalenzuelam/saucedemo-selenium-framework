package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginBeforeInventory() {
        loginPage = new LoginPage(driver);
        // Usamos Fluent Interface para inicializar inventoryPage
        inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");
    }

    @Test(priority = 1, description = "Validar que el título de la página es Products")
    public void testInventoryTitle() {
        // CORRECCIÓN: Volvemos al título real para que el test pase en GitHub Actions
        Assert.assertEquals(inventoryPage.getTitle(), "Products", "El título de la página de inventario es incorrecto.");
    }

    @Test(priority = 2, description = "Validar que hay 6 productos en la lista")
    public void testProductCount() {
        Assert.assertEquals(inventoryPage.getInventoryItemsCount(), 6);
    }

    @Test(priority = 3, description = "Validar que al agregar un producto el carrito se actualiza a 1")
    public void testAddProductToCart() {
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");
    }

    @Test(priority = 4, description = "Validar que al remover un producto el carrito vuelve a 0")
    public void testRemoveProductFromCart() {
        inventoryPage.addFirstItemToCart(); // Primero agregamos
        // Esperamos a que el badge sea "1" antes de intentar removerlo
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1", "El producto no se agregó al carrito.");
        
        inventoryPage.removeFirstItemFromCart(); // Luego removemos
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "0", "El carrito no se vació.");
    }

    @Test(priority = 5, description = "Validar ordenamiento por precio (Low to High)")
    public void testSortByPriceLowToHigh() {
        inventoryPage.selectSortOption("Price (low to high)");
        String firstPrice = inventoryPage.getFirstItemPrice();
        Assert.assertEquals(firstPrice, "$7.99", "El primer producto no es el más barato.");
    }
}
