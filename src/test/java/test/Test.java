package test;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class Test {

    public static void main(String[] args) {
        By selector = By.id("new UiSelector().text(\"Total a pagar:\")");
        System.out.println(selector.toString());
    }
}
