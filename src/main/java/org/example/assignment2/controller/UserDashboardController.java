package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;

public class UserDashboardController {

    @FXML
    private Text welcomeMessage;  // The welcome message should be of type Text, not Label

    @FXML
    private TableView<?> topBooksTable;  // Table for top 5 books

    // Method to set the welcome message after login
    public void setWelcomeMessage(String message) {
        welcomeMessage.setText(message);
    }

    // Handle View Profile action
    @FXML
    private void handleViewProfile() {
        // Logic to view user profile
        System.out.println("View Profile clicked");
    }

    // Handle View Cart action
    @FXML
    private void handleViewCart() {
        // Logic to view user cart
        System.out.println("View Cart clicked");
    }

    // Handle View Orders action
    @FXML
    private void handleViewOrders() {
        // Logic to view user orders
        System.out.println("View Orders clicked");
    }

    // Handle Logout action
    @FXML
    private void handleLogout() {
        // Logic to logout the user, and possibly navigate back to the login screen
        System.out.println("User logged out.");
    }

    // Handle Shop Now action
    @FXML
    private void handleShopNow() {
        // Logic for shopping or browsing books
        System.out.println("Shop Now clicked");
    }
}
