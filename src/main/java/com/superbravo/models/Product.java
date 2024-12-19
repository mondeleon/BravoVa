package com.superbravo.models;

public record Product (String name, double price, int quantity) {

    public Product {
        if (price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
    }

    public double totalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Producto: " + name + ", precio: $" + price + ", cantidad: " + quantity;
    }
}
