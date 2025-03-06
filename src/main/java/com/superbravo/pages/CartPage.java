package com.superbravo.pages;

import com.superbravo.mobile.MobileActions;
import com.superbravo.models.Cart;
import com.superbravo.models.Product;
import com.superbravo.utils.Prize;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartPage {

    public MobileActions actions;

    final private By CONFIRM_ORDER = AppiumBy.id("com.superbravo.adomicilio.staging:id/rlConfirmarCompra");
    final private By PRODUCTS_NAME = AppiumBy.id("com.superbravo.adomicilio.staging:id/tvName");
    final private By PRODUCTS_QUANTITY = AppiumBy.id("com.superbravo.adomicilio.staging:id/tvCantidad");
    final private By ORDER_TOTAL = AppiumBy.xpath("(//*[@resource-id=\"com.superbravo.adomicilio.staging:id/tvParteEntera\"])[last()]");
    final private By PRODUCTS_PRIZE = AppiumBy.xpath("//*[@resource-id=\"com.superbravo.adomicilio.staging:id/cpPrecio\"]//*[contains(@text, \"$\")]");
    final private By PRODUCTS_TOTAL = AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView//*[ends-with(@resource-id, \"cpTotal\")]//*[contains(@text, \"$\")]");
    final private String DELETE_PRODUCT = "new UiSelector().resourceId(\"com.superbravo.adomicilio.staging:id/ivEliminar\").instance(%s)";

    public CartPage(MobileActions actions){
        this.actions = actions;
    }

    public double getTotal(){
        String totalTxt = actions.getText(ORDER_TOTAL);
        return Prize.toDouble(totalTxt);
    }

    public void confirmOrder(){
        actions.tapElement(CONFIRM_ORDER);
    }

    public Map<Integer, String> getProductsName(){
        List<WebElement> elements = actions.findElements(PRODUCTS_NAME);
        return elements.stream()
                .collect(Collectors.toMap(
                        elements::indexOf,
                        WebElement::getText
                ));
    }

    public Map<Integer, Double> getProductsPrize(){
        List<WebElement> elements = actions.findElements(PRODUCTS_PRIZE);
        return elements.stream()
                .collect(Collectors.toMap(
                        elements::indexOf,
                        element -> Prize.toDouble(element.getText())
                ));
    }

    public Map<Integer, Integer> getProductsQuantity(){
        List<WebElement> elements = actions.findElements(PRODUCTS_QUANTITY);
        return elements.stream()
                .collect(Collectors.toMap(
                        elements::indexOf,
                        element -> Integer.parseInt(element.getText())
                ));
    }

    public Map<Integer, Double> getProductsTotal(){
        List<WebElement> elements = actions.findElements(PRODUCTS_TOTAL);
        return elements.stream()
                .collect(Collectors.toMap(
                        elements::indexOf,
                        element -> Prize.toDouble(element.getText())
                ));
    }

    public List<Product> getProducts(){
        Map<Integer, String> productsName = getProductsName();
        Map<Integer, Double> productsPrize = getProductsPrize();
        Map<Integer, Integer> productsQuantity = getProductsQuantity();

        int cartSize = productsName.size();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < cartSize; i++) {
            String productName = productsName.get(i);
            Double prize = productsPrize.get(i);
            int quantity = productsQuantity.get(i);
            products.add(new Product(productName, prize, quantity));
        }
        return products;
    }

    public void deleteProduct(int index){
        By selector = AppiumBy.androidUIAutomator(DELETE_PRODUCT.formatted(index));
        actions.tapElement(selector);
    }

    public Cart getCart(){
        List<Product> products = getProducts();
        return new Cart(products);
    }
}
