package com.superbravo.driver;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    public static final int DEFAULT_TIMEOUT = 5;
    private static AppiumDriverLocalService appiumServer;
    private static AndroidDriver driver;
    private static final String APP_PACKAGE = "com.superbravo.adomicilio.staging";

    public static void setUpDriver(String udid) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid(udid);
        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUpAppiumSever(){
        appiumServer = AppiumDriverLocalService.buildDefaultService();
        appiumServer.start();
    }

    public static void tearDownAppiumSever(){
        driver.quit();
        appiumServer.stop();
    }

    public static AndroidDriver getDriver(){
        return driver;
    }

    public static void startApplication(){
        if (driver == null){
            throw new RuntimeException("Driver is null, run setUp method after!");
        }
        driver.activateApp(APP_PACKAGE);
    }

    public static void restartApplication(){
        if (driver == null){
            throw new RuntimeException("Driver is null, run setUp method after!");
        }
        driver.terminateApp(APP_PACKAGE);
        driver.activateApp(APP_PACKAGE);
    }
}
