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
        return driver.findElement(totalLabel).getText();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }
}
