package org.example.assignment2.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.assignment2.model.Order;
import org.example.assignment2.model.OrderItem;
import org.example.assignment2.model.OrderManager;
import javafx.stage.FileChooser;
import org.example.assignment2.model.UserManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrderHistoryController {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderNumberColumn;
    @FXML
    private TableColumn<Order, String> dateTimeColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableView<OrderItem> orderItemsTable;
    @FXML
    private TableColumn<OrderItem, String> bookTitleColumn;
    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TableColumn<OrderItem, Double> priceColumn;
    @FXML
    private TableColumn<OrderItem, Double> itemTotalPriceColumn;

    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

    private final OrderManager orderManager = OrderManager.getInstance();

    @FXML
    public void initialize() {
        // Set up the columns for orders
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        dateTimeColumn.setCellValueFactory(order -> new SimpleStringProperty(order.getValue().getFormattedOrderDatetime()));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Set up the columns for order items
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Disable resizing for all columns in both tables
        orderNumberColumn.setResizable(false);
        dateTimeColumn.setResizable(false);
        totalPriceColumn.setResizable(false);

        bookTitleColumn.setResizable(false);
        quantityColumn.setResizable(false);
        priceColumn.setResizable(false);
        itemTotalPriceColumn.setResizable(false);

        // Load orders into the table
        loadOrders();

        // Add a listener to load order items when an order is selected
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadOrderItems(newValue.getOrderId());
            }
        });
    }


    private void loadOrders() {
        // Retrieve only the orders for the current user
        int currentUserId = UserManager.getInstance().getCurrentUser().getUserId();
        List<Order> orderList = orderManager.getOrdersByUserId(currentUserId); // Adjusted to filter by user

        orders.setAll(orderList);  // Set the filtered orders to the observable list
        orderTable.setItems(orders);  // Set the observable list to the table view
    }


    private void loadOrderItems(int orderId) {
        List<OrderItem> orderItemList = orderManager.getOrderItems(orderId);
        orderItems.setAll(orderItemList);
        orderItemsTable.setItems(orderItems);
    }

    @FXML
    private void handleBackAction() {
        try {
            // Load the UserDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UserDashboard.fxml"));
            Parent root = loader.load();

            // Get the current stage using any node in this scene (like orderTable)
            Stage stage = (Stage) orderTable.getScene().getWindow();

            // Set the new scene and update the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("User Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load User Dashboard.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Handler to export CSV
    @FXML
    private void handleExportCSV() {
        // Get the selected orders from the table
        ObservableList<Order> selectedOrders = orderTable.getSelectionModel().getSelectedItems();

        // Check if any orders are selected
        if (selectedOrders == null || selectedOrders.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select at least one order to export.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Selected Order(s)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(orderTable.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Writing the header for orders
                writer.write("Order Number,Date & Time,Total Price\n");
                for (Order order : selectedOrders) {
                    writer.write(order.getOrderId() + "," + order.getFormattedOrderDatetime() + "," + order.getTotalPrice() + "\n");
                }

                // Writing the header for order items
                writer.write("\nOrder Items\nOrder Number,Book Title,Quantity,Price,Total Price\n");
                for (Order order : selectedOrders) {
                    List<OrderItem> orderItemList = orderManager.getOrderItems(order.getOrderId());
                    for (OrderItem item : orderItemList) {
                        writer.write(order.getOrderId() + "," + item.getBookTitle() + "," + item.getQuantity() + "," + item.getPrice() + "," + item.getTotalPrice() + "\n");
                    }
                }

                // Show confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CSV file saved successfully!", ButtonType.OK);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save CSV file.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

}
