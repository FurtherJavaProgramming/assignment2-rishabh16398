package org.example.assignment2.model;

import javafx.beans.property.*;

public class OrderItem {
    private final StringProperty bookTitle;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final DoubleProperty totalPrice;

    // Constructor
    public OrderItem(String bookTitle, int quantity, double price) {
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.totalPrice = new SimpleDoubleProperty(price * quantity);
    }

    // Getters for properties (used by JavaFX TableView)
    public StringProperty bookTitleProperty() {
        return bookTitle;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    // Getters for values
    public String getBookTitle() {
        return bookTitle.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    // Setters
    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        this.totalPrice.set(this.price.get() * quantity);  // Update total price when quantity changes
    }

    public void setPrice(double price) {
        this.price.set(price);
        this.totalPrice.set(price * this.quantity.get());  // Update total price when price changes
    }
}
