package com.superbravo.mobile;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static com.superbravo.driver.DriverManager.DEFAULT_TIMEOUT;

public class MobileActions {

    private static final Logger log = LoggerFactory.getLogger(MobileActions.class);

    public AppiumDriver driver;

    public MobileActions(AppiumDriver driver){
        this.driver = driver;
    }

    public WebElement findElement(By by){
        try{
            return driver.findElement(by);
        } catch (NoSuchElementException e){
            log.info("Elemento no encontrado: " + by.toString());
        }
        return null;
    }

    public List<WebElement> findElements(By by){
        try{
            return driver.findElements(by);
        } catch (NoSuchElementException e){
            log.info("Elementos no encontrados: " + by.toString());
        }
        return null;
    }


    public void pressKey(AndroidKey key){
        AndroidDriver androidDriver = (AndroidDriver) driver;
        androidDriver.pressKey(new KeyEvent(key));
    }

    public void tapElement(By by){
        WebElement element = findElement(by);
        if (element == null){
            log.info("No se puede hacer click en este elemento: " + by.toString());
            return;
        }
        element.click();
    }

    public void setText(By by, String text){
        WebElement element = findElement(by);
        if (element == null){
            log.info("No se puede enviar texto a est elemento: " + by.toString());
            return;
        }
        element.sendKeys(text);
    }

    public String getText(By by){
        WebElement element = findElement(by);
        if (element == null){
            log.info("No se puede obtener el texto de este elemento: " + by.toString());
            return null;
        }
       return element.getText();
    }

    public boolean isElementPresent(By by) {
        return isElementPresent(by, 3);
    }

    public boolean isElementPresent(By by, int timeOut) {
        setTimeOut(Duration.ofSeconds(timeOut));
        WebElement element = findElement(by);
        setTimeOut(Duration.ofSeconds(DEFAULT_TIMEOUT));
        return element != null;
    }

    public <X> X takeScreenshot(final OutputType<X> outputType) {
        return driver.getScreenshotAs(outputType);
    }

    public void setTimeOut(Duration duration){
        driver.manage().timeouts().implicitlyWait(duration);
    }

    public boolean waitForElementNotPresent(By element, int timeOut){
        setTimeOut(Duration.ofMillis(500L));
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.invisibilityOfElementLocated(element));
        } finally {
            setTimeOut(Duration.ofSeconds(DEFAULT_TIMEOUT));
        }
    }

    public boolean waitForElementPresent(By element, int timeOut){
        setTimeOut(Duration.ofMillis(500L));
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.presenceOfElementLocated(element)).isDisplayed();
        } finally {
            setTimeOut(Duration.ofSeconds(DEFAULT_TIMEOUT));
        }
    }

    public void delay(long milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void performEditorAction(String action){
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", action));
    }

    public void scrollAndClick(String visibleText) {
        scroll(visibleText, 0).click();
    }

    public void scrollAndClick(String visibleText, int index) {
        scroll(visibleText, index).click();
    }

    public WebElement scroll(String visibleText) {
        return scroll(visibleText, 0);
    }

    public WebElement scroll(String visibleText, int index) {
        By selector = AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+visibleText+"\").instance( " + index +"))");
        return driver.findElement(selector);
    }
}
