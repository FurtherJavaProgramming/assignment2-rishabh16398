package org.example.assignment2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private int orderId;
    private LocalDateTime orderDateTime;
    private double totalPrice;

    // Constructor
    public Order(int orderId, LocalDateTime orderDateTime, double totalPrice) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Return formatted datetime as string
    public String getFormattedOrderDatetime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return orderDateTime.format(formatter);
    }

    // Setters (if necessary, depending on your requirements)
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Date: " + getFormattedOrderDatetime() + ", Total Price: $" + totalPrice;
    }
}
