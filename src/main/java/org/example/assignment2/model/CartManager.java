package org.example.assignment2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartManager {

    // Singleton instance
    private static CartManager instance;

    // The cart items list
    private ObservableList<CartItem> cartItems;

    // Private constructor
    private CartManager() {
        cartItems = FXCollections.observableArrayList();
    }

    // Method to get the singleton instance
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Add book to cart or update quantity if already exists
    public void addToCart(Book book, int quantity) {
        boolean itemExists = false;
        for (CartItem item : cartItems) {
            if (item.getBook().equals(book)) {
                item.setQuantity(item.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            cartItems.add(new CartItem(book, quantity));
        }
    }

    // Update quantity of a book in the cart
    public void updateCart(Book book, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().equals(book)) {
                item.setQuantity(quantity);
                break;
            }
        }
    }

    // Remove a book from the cart
    public void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    // Retrieve the cart contents as an ObservableList
    public ObservableList<CartItem> getCartContents() {
        return cartItems;
    }

    // Clear the cart
    public void clearCart() {
        cartItems.clear();
    }

    // Calculate total price
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // Check if any items in the cart exceed stock
    public boolean hasStockIssues() {
        for (CartItem item : cartItems) {
            if (item.getQuantity() > item.getBook().getStock()) {
                return true;
            }
        }
        return false;
    }
}
