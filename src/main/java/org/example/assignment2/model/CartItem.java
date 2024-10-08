package org.example.assignment2.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CartItem {
    private final Book book;
    private final IntegerProperty quantity;
    private final DoubleProperty totalPrice;

    // Constructor
    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalPrice = new SimpleDoubleProperty(calculateTotalPrice());

        // Update total price whenever quantity changes
        this.quantity.addListener((obs, oldVal, newVal) -> updateTotal());
    }

    // Getter for book object
    public Book getBook() {
        return book;
    }

    public String getTitle() {
        return book.getTitle();
    }

    public double getPrice() {
        return book.getPrice();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    // Method to calculate and update total price
    private double calculateTotalPrice() {
        return book.getPrice() * getQuantity();
    }

    public void updateTotal() {
        totalPrice.set(calculateTotalPrice());
    }
}
