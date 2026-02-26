package com.saucedemo.tests;

import com.saucedemo.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage stepOnePage;
    private CheckoutStepTwoPage stepTwoPage;
    private CheckoutCompletePage completePage;

    @BeforeMethod
    public void setupCheckout() {
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);

        // Preparación: Login -> Add to Cart -> Cart -> Checkout usando Fluent Interface
        inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();
        stepOnePage = cartPage.clickCheckout();
    }

    @Test(priority = 1, description = "Validar el flujo completo de compra exitosa")
    public void testSuccessfulCheckout() {
        stepOnePage.enterInformation("Chris", "Automation", "00000");
        stepTwoPage = stepOnePage.clickContinue();

        // Validamos que estamos en el resumen (Overview)
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"));
        completePage = stepTwoPage.clickFinish();

        // Validamos el mensaje final
        Assert.assertEquals(completePage.getConfirmationMessage(), "Thank you for your order!");
    }

    @Test(priority = 2, description = "Validar validación de campos obligatorios en el checkout")
    public void testCheckoutMandatoryFields() {
        stepOnePage.enterInformation("", "", "");
        stepOnePage.clickContinue(); // No guardamos el resultado porque esperamos error en la misma página
        
        Assert.assertEquals(stepOnePage.getErrorMessage(), "Error: First Name is required");
    }
}
