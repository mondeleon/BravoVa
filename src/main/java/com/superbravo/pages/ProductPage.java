package com.superbravo.pages;

import com.superbravo.mobile.MobileActions;
import com.superbravo.utils.Prize;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ProductPage {

    public MobileActions actions;

    private final By QUANTITY = AppiumBy.id("com.superbravo.adomicilio.staging:id/tvCantidad");
    private final By QUANTITY_EDITOR = AppiumBy.id("com.superbravo.adomicilio.staging:id/tiet");
    private final By ACCEPT_BTN = AppiumBy.id("com.superbravo.adomicilio.staging:id/btnOk");
    private final By ADD_TO_CART_BTN = AppiumBy.id("com.superbravo.adomicilio.staging:id/btnAnyadir");
    private final By PRODUCT_PRIZE = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.superbravo.adomicilio.staging:id/tvParteEntera\").instance(0)");
    private final By TOTAL = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.superbravo.adomicilio.staging:id/tvParteEntera\").instance(1)");

    public ProductPage(MobileActions actions){
        this.actions = actions;
    }

    public void addProductToCart(int quantity){
        tapQuantity();
        setQuantity(quantity);
        tapAccept();
        addToCart();
    }

    public void tapQuantity() {
        actions.tapElement(QUANTITY);
    }

    public void setQuantity(int quantity){
        actions.setText(QUANTITY_EDITOR, String.valueOf(quantity));
    }

    public void tapAccept(){
        actions.tapElement(ACCEPT_BTN);
    }

    public void addToCart(){
        actions.tapElement(ADD_TO_CART_BTN);
    }

    public double getExpectedTotal(){
        String prize = actions.getText(PRODUCT_PRIZE);
        String quantity = actions.getText(QUANTITY);

        double prizeValue = Prize.toDouble(prize);
        double quantityValue = Prize.toDouble(quantity);
        return prizeValue * quantityValue;
    }

    public boolean waitForPagePresent(){
        return actions.waitForElementPresent(ADD_TO_CART_BTN, 10);
    }

    public double getTotal() {
        String total = actions.getText(TOTAL);
        return Prize.toDouble(total);
    }
}
