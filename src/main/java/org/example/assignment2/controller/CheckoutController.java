// CheckoutController.java
package org.example.assignment2.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.CartItem;
import org.example.assignment2.model.CartManager;
import org.example.assignment2.model.OrderManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CheckoutController {

    @FXML
    private TableView<CartItem> cartTable;
    @FXML
    private TableColumn<CartItem, String> productColumn;
    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;
    @FXML
    private TableColumn<CartItem, Double> priceColumn;
    @FXML
    private TableColumn<CartItem, Double> totalColumn;

    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expiryDateField;
    @FXML
    private TextField cvvField;

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label errorLabel;

    @FXML
    private Button confirmOrderButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button backButton;

    private final CartManager cartManager = CartManager.getInstance();
    private final OrderManager orderManager = OrderManager.getInstance();

    @FXML
    private void initialize() {
        productColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        loadCartItems();
        updateTotalPrice();
    }

    private void loadCartItems() {
        ObservableList<CartItem> cartItems = cartManager.getCartContents();
        cartTable.setItems(cartItems);
    }

    private void updateTotalPrice() {
        double totalPrice = cartManager.getTotalPrice();
        totalPriceLabel.setText(String.format("$%.2f", totalPrice));
    }

    @FXML
    private void handleConfirmPayment() {
        if (cartManager.getCartContents().isEmpty()) {
            errorLabel.setText("Your cart is empty. Add items to checkout.");
            return;
        }

        // Retrieve input values
        String cardNumber = cardNumberField.getText().trim();
        String expiryDate = expiryDateField.getText().trim();
        String cvv = cvvField.getText().trim();

        // Validate the card number (16 digits)
        if (!cardNumber.matches("\\d{16}")) {
            errorLabel.setText("Invalid card number. It must be 16 digits.");
            return;
        }

        // Validate the expiry date (future date)
        if (!isExpiryDateValid(expiryDate)) {
            errorLabel.setText("Invalid expiry date. It must be in the future.");
            return;
        }

        // Validate the CVV (3 digits)
        if (!cvv.matches("\\d{3}")) {
            errorLabel.setText("Invalid CVV. It must be 3 digits.");
            return;
        }

        // If all validations pass, proceed with placing the order
        boolean orderPlaced = orderManager.placeOrder(cartManager.getCartContents(), cartManager.getTotalPrice(), LocalDateTime.now());

        if (orderPlaced) {
            cartManager.clearCart();
            errorLabel.setText("Order placed successfully.");
        } else {
            errorLabel.setText("Failed to place the order. Please try again.");
        }
    }


    private boolean isExpiryDateValid(String expiryDate) {
        try {
            // Parse the expiry date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiry = YearMonth.parse(expiryDate, formatter);

            // Check if the expiry date is in the future
            YearMonth currentMonth = YearMonth.now();
            return expiry.isAfter(currentMonth);
        } catch (DateTimeParseException e) {
            // Invalid date format
            return false;
        }
    }

    @FXML
    private void handleCancelOrder() {
        try {
            // Load the Shopping Cart scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/ShoppingCart.fxml"));
            Parent root = loader.load();

            // Set the scene to the current stage
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Shopping Cart");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            // Load the User Dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UserDashboard.fxml"));
            Parent root = loader.load();

            // Set the scene to the current stage
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
