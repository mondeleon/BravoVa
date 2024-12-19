package com.superbravo.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import com.superbravo.mobile.MobileActions;

public class LoginPage {

    public MobileActions actions;

    private final By WELCOME = AppiumBy.androidUIAutomator("new UiSelector().text(\"¡Bienvenido a BravoVa!\")");
    private final By LOGIN_BTN = AppiumBy.androidUIAutomator("new UiSelector().text(\"Iniciar sesión\")");
    private final By ID_INPUT = AppiumBy.id("com.superbravo.adomicilio.staging:id/tietDocumento");
    private final By PASSWORD_INPUT = AppiumBy.id("com.superbravo.adomicilio.staging:id/tietPassword");
    private final By SKIP_BIOMETRY= AppiumBy.id("com.superbravo.adomicilio.staging:id/tvSaltar");
    private final By HELLO_USER = AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"¡Hola[A-Z ]*!\")");

    public LoginPage(MobileActions actions){
        this.actions = actions;
    }

    public void signUp(String user, String password){
        if (actions.isElementPresent(WELCOME, 2)){
            tapLogin();
        }
        setUser(user);
        setPassword(password);
        tapLogin();
        skipBiometry();
    }

    public void logIn(String user, String password){
        if (!actions.isElementPresent(HELLO_USER)){
            signUp(user, password);
            return;
        }
        setPassword(password);
        tapLogin();
    }

    public boolean isUserLogged(){
        return actions.isElementPresent(HELLO_USER, 2);
    }

    public boolean isUserRegistered(){
        return !actions.isElementPresent(WELCOME, 2);
    }

    public LoginPage setUser(String user){
        actions.setText(ID_INPUT, user);
        return this;
    }

    public LoginPage setPassword(String password){
        actions.setText(PASSWORD_INPUT, password);
        return this;
    }

    public void tapLogin(){
        actions.tapElement(LOGIN_BTN);
    }

    public void skipBiometry(){
        actions.tapElement(SKIP_BIOMETRY);
    }

    public boolean waitForPagePresent(){
        return actions.waitForElementPresent(LOGIN_BTN, 10);
    }
}
