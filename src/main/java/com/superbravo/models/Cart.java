package com.superbravo.models;

import java.util.ArrayList;
import java.util.List;

public record Cart(List<Product> products) {

    public Cart(){
       this(new ArrayList<>());
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public double totalAmount() {
        return products.stream()
                .mapToDouble(Product::totalPrice)
                .sum();
    }

    public int totalItems() {
        return products.stream()
                .mapToInt(Product::quantity)
                .sum();
    }

    public void clean() {
        products.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int counter = 1;
        builder.append("Productos del carro de compras:");
        for (Product p : products){
            builder.append("\n")
                    .append(" ")
                    .append(counter)
                    .append(". ")
                    .append(p.toString());
            counter++;
        }
        builder.append("\nTotal: ").append(totalAmount());
        return builder.toString();
    }
}
