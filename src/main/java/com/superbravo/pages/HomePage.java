package com.superbravo.pages;

import com.superbravo.mobile.MobileActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomePage {

    public MobileActions actions;

    private final By SEARCH_BAR = AppiumBy.id("com.superbravo.adomicilio.staging:id/tvSearch");
    private final By SHOPPING_CAR = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.superbravo.adomicilio.staging:id/ivCart\").instance(0)");
    private final By SEARCH_BAR_EDITOR = AppiumBy.id("com.superbravo.adomicilio.staging:id/etSearch");
    private final By NUMBER_ARTICLES = AppiumBy.id("com.superbravo.adomicilio.staging:id/tvNumArticulos");

    public HomePage(MobileActions actions){
        this.actions = actions;
    }

    public HomePage searchProduct(String product){
        actions.tapElement(SEARCH_BAR);
        actions.delay(1000L);
        actions.setText(SEARCH_BAR_EDITOR, product);
        actions.performEditorAction("done");
        return this;
    }

    public void goToCart(){
        actions.tapElement(SHOPPING_CAR);
    }

    public void goToProductDetails(String product){
        actions.scrollAndClick(product, 1);
    }

    public boolean isPagePresent(){
        return actions.isElementPresent(SEARCH_BAR, 2);
    }

    public boolean waitForPagePresent(){
        return actions.waitForElementPresent(SHOPPING_CAR, 50);
    }

    public int getNumberOfArticles(){
        return Integer.parseInt(actions.getText(NUMBER_ARTICLES));
    }
}
