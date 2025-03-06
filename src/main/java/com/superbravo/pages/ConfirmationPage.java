package com.superbravo.pages;

import com.superbravo.mobile.MobileActions;
import com.superbravo.utils.Prize;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ConfirmationPage {

    public MobileActions actions;

    private static final By SUB_TOTAL = By.xpath("(//*[@resource-id = \"com.superbravo.adomicilio.staging:id/tvParteEntera\"])[1]");
    private static final By ITBIS = By.xpath("(//*[@resource-id = \"com.superbravo.adomicilio.staging:id/tvParteEntera\"])[2]");
    private static final By COST_SHIPPING = By.xpath("(//*[@resource-id = \"com.superbravo.adomicilio.staging:id/tvParteEntera\"])[3]");
    private static final By TOTAL = By.xpath("//*[@resource-id=\"com.superbravo.adomicilio.staging:id/cpTotal\"]/*/*");
    private static final By MAKE_PAYMENT = By.id("com.superbravo.adomicilio.staging:id/btnCrearPedido");

    //Pago confirmado
    private static final By CONTINUE = By.id("com.superbravo.adomicilio.staging:id/btnContinuar");
    private static final By ARTICLES = By.id("com.superbravo.adomicilio.staging:id/tvNumArticulos");
    private static final By TITLE = By.id("com.superbravo.adomicilio.staging:id/tvTitulo");

    public ConfirmationPage(MobileActions actions){
        this.actions = actions;
    }

    public void tapContinue(){
        actions.tapElement(CONTINUE);
    }

    public int getArticles(){
        return Integer.parseInt(actions.getText(ARTICLES));
    }

    public String getTile(){
        return actions.getText(TITLE);
    }

    public double getSubTotal(){
        String subTotalTxt = actions.getText(SUB_TOTAL);
        return Prize.toDouble(subTotalTxt);
    }

    public double getITBIS(){
        String subTotalTxt = actions.getText(ITBIS);
        return Prize.toDouble(subTotalTxt);
    }

    public double getCostShipping(){
        String subTotalTxt = actions.getText(COST_SHIPPING);
        return Prize.toDouble(subTotalTxt);
    }

    public double getTotal(){
        actions.scroll("Total a pagar");
        String subTotalTxt = actions.getText(TOTAL);
        return Prize.toDouble(subTotalTxt);
    }

    public double getExpectedTotal(){
        double subTotal = getSubTotal();
        double ITBIS = getITBIS();
        double costShipping = getCostShipping();
        return subTotal + ITBIS + costShipping;
    }

    public void tapMakePayment(){
        actions.tapElement(MAKE_PAYMENT);
    }
}
