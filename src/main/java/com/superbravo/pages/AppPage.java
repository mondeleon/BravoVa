package com.superbravo.pages;

import com.superbravo.driver.DriverManager;
import com.superbravo.mobile.MobileActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;

public class AppPage {

    private static AppPage _instance;
    public LoginPage loginPage;
    public BasePage basePage;
    public HomePage homePage;
    public ProductPage productPage;
    public CartPage cartPage;

    private AppPage(AppiumDriver driver){
        MobileActions actions = new MobileActions(driver);
        loginPage = new LoginPage(actions);
        basePage = new BasePage(actions);
        homePage = new HomePage(actions);
        productPage = new ProductPage(actions);
        cartPage = new CartPage(actions);

    }

    public static AppPage getInstance(){
        if(_instance == null){
            AndroidDriver driver = DriverManager.getDriver();
            _instance = new AppPage(driver);
        }
        return _instance;
    }

    public byte[] takeScreenshot(){
        return basePage.takeScreenshot(OutputType.BYTES);
    }

    public String takeScreenshotBase64(){
        return basePage.takeScreenshot(OutputType.BASE64);
    }

    public boolean awaitProcessing(int timeOut){
       return basePage.awaitLoading(timeOut);
    }

    public void delay(long timeOut){
        basePage.actions.delay(timeOut);
    }
}
