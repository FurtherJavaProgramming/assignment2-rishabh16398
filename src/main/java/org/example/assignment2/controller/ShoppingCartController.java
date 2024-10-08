package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.assignment2.model.CartItem;
import org.example.assignment2.model.CartManager;

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
    private Label stockWarningLabel;
    @FXML
    private Label totalPriceLabel;

    private final CartManager cartManager = CartManager.getInstance();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Set up quantity column with Spinners
        quantityColumn.setCellFactory(column -> new SpinnerTableCell());

        // Set up actions column with Remove buttons
        actionsColumn.setCellFactory(column -> new TableCell<CartItem, Void>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    cartManager.removeFromCart(item);
                    updateTotalPrice();
                    checkStockAvailability();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        cartTable.setItems(cartManager.getCartContents());
        updateTotalPrice();
        checkStockAvailability();
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
                    checkStockAvailability();
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
                spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, cartItem.getBook().getStock(), cartItem.getQuantity()));
                setGraphic(spinner);
            }
        }
    }

    private void updateTotalPrice() {
        double total = cartManager.getTotalPrice();
        totalPriceLabel.setText(String.format("Total Price: $%.2f", total));
    }

    private void checkStockAvailability() {
        boolean hasStockIssues = cartManager.hasStockIssues();
        stockWarningLabel.setVisible(hasStockIssues);
        if (hasStockIssues) {
            stockWarningLabel.setText("Warning: Selected quantity exceeds available stock for some items.");
        } else {
            stockWarningLabel.setText("");
        }
    }

    @FXML
    private void handleCheckoutAction() {
        if (!cartManager.hasStockIssues()) {
            System.out.println("Proceeding to checkout...");
            // Checkout logic here
        } else {
            stockWarningLabel.setText("Adjust quantities - some items exceed stock.");
        }
    }

    @FXML
    private void handleBackAction() {
        System.out.println("Returning to Shop Now...");
        // Back to Shop Now logic here
    }
}
