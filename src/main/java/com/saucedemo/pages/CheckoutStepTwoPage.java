package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage extends BasePage {

    private By finishButton = By.id("finish");
    private By totalLabel = By.className("summary_total_label");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public String getTotal() {
        return wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(totalLabel)).getText();
    }

    public CheckoutCompletePage clickFinish() {
        click(finishButton);
        waitForUrlContains("checkout-complete.html");
        return new CheckoutCompletePage(driver);
    }
}
