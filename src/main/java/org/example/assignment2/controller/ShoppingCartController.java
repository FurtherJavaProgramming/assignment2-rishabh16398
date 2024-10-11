// ShoppingCartController.java
package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.assignment2.model.CartItem;
import org.example.assignment2.model.CartManager;
import javafx.stage.Stage;

import java.io.IOException;

public class ShoppingCartController {

    @FXML
    private TableView<CartItem> cartTable;
    @FXML
    private TableColumn<CartItem, String> titleColumn;
    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;
    @FXML
    private TableColumn<CartItem, Double> priceColumn;
    @FXML
    private TableColumn<CartItem, Double> totalColumn;
    @FXML
    private TableColumn<CartItem, Void> actionsColumn;
    @FXML
    private Label totalPriceLabel;

    private final CartManager cartManager = CartManager.getInstance();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        quantityColumn.setCellFactory(column -> new SpinnerTableCell());

        actionsColumn.setCellFactory(column -> new TableCell<CartItem, Void>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    cartManager.removeFromCart(item);
                    updateTotalPrice();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeButton);
            }
        });

        cartTable.setItems(cartManager.getCartContents());
        updateTotalPrice();
    }

    private class SpinnerTableCell extends TableCell<CartItem, Integer> {
        private final Spinner<Integer> spinner = new Spinner<>();

        public SpinnerTableCell() {
            spinner.setEditable(true);
            spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                CartItem cartItem = getTableView().getItems().get(getIndex());
                if (newVal != null) {
                    cartItem.setQuantity(newVal);
                    cartItem.updateTotal();
                    updateTotalPrice();
                }
            });
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                CartItem cartItem = getTableView().getItems().get(getIndex());
                spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, cartItem.getQuantity()));
                setGraphic(spinner);
            }
        }
    }

    private void updateTotalPrice() {
        double total = cartManager.getTotalPrice();
        totalPriceLabel.setText(String.format("Total Price: $%.2f", total));
    }

    @FXML
    private void handleCheckoutAction() {
        cartManager.updateStockFromDatabase(); // Fetch latest stock levels from the database

        boolean hasStockIssues = false;
        StringBuilder stockIssuesMessage = new StringBuilder("Insufficient stock for:\n");

        for (CartItem item : cartManager.getCartContents()) {
            if (item.getQuantity() > item.getBook().getStock()) {
                hasStockIssues = true;
                stockIssuesMessage.append("- ").append(item.getBook().getTitle())
                        .append(" (Available: ").append(item.getBook().getStock())
                        .append(", Requested: ").append(item.getQuantity()).append(")\n");
            }
        }

        if (hasStockIssues) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stock Unavailable");
            alert.setHeaderText("One or more items exceed available stock.");
            alert.setContentText(stockIssuesMessage.toString());
            alert.showAndWait();
        } else {
            System.out.println("Proceeding to checkout...");
            // Continue to checkout
        }
    }

    @FXML
    private void handleBackAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/ShopNow.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) cartTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Shop Now");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBackToDashboardAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UserDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) cartTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
