package com.superbravo.pages;

import com.superbravo.mobile.MobileActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

public class BasePage {

    MobileActions actions;

    private final By PROGRESS_BAR = AppiumBy.xpath("//android.widget.ProgressBar[1]");
    private final By GO_BACK = AppiumBy.androidUIAutomator("new UiSelector().description(\"Navigate up\")");

    public BasePage(MobileActions actions){
        this.actions = actions;
    }

    public <X> X takeScreenshot(final OutputType<X> outputType) {
        return actions.takeScreenshot(outputType);
    }

    public boolean awaitLoading(int timeOut){
        if (actions.isElementPresent(PROGRESS_BAR, 1)){
           return actions.waitForElementNotPresent(PROGRESS_BAR, timeOut);
        }
        //System.out.println(actions.driver.getPageSource());
        return true;
    }

    public BasePage goBack(){
        actions.tapElement(GO_BACK);
        return this;
    }

}
