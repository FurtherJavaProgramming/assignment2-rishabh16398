// CheckoutController.java
package org.example.assignment2.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.CartItem;
import org.example.assignment2.model.CartManager;
import org.example.assignment2.model.OrderManager;
import java.time.LocalDateTime;

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

        boolean orderPlaced = orderManager.placeOrder(cartManager.getCartContents(), cartManager.getTotalPrice(), LocalDateTime.now());

        if (orderPlaced) {
            cartManager.clearCart();
            errorLabel.setText("Order placed successfully.");
        } else {
            errorLabel.setText("Failed to place the order. Please try again.");
        }
    }

    @FXML
    private void handleCancelOrder() {
        cartManager.clearCart();
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    private void handleBackToDashboard() {
        // Logic to navigate back to the UserDashboard scene
        System.out.println("Back to Dashboard clicked");
    }
}
