package org.example.assignment2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.BookManager;

import java.io.IOException;

public class UpdateProductController {

    @FXML
    private ComboBox<Book> productNameComboBox;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productStockField;

    private final BookManager bookManager = BookManager.getInstance();

    @FXML
    private void initialize() {
        // Load books into the ComboBox
        loadProducts();

        // Set up a listener to update fields when a product is selected
        productNameComboBox.setOnAction(event -> populateProductDetails());
    }

    private void loadProducts() {
        ObservableList<Book> books = FXCollections.observableArrayList(bookManager.getAllBooks());
        productNameComboBox.setItems(books);
    }

    private void populateProductDetails() {
        Book selectedBook = productNameComboBox.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            productPriceField.setText(String.valueOf(selectedBook.getPrice()));
            productStockField.setText(String.valueOf(selectedBook.getStock()));
        }
    }

    @FXML
    private void handleSaveChanges() {
        Book selectedBook = productNameComboBox.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("No Product Selected", "Please select a product to update.");
            return;
        }

        try {
            double newPrice = Double.parseDouble(productPriceField.getText());
            int newStock = Integer.parseInt(productStockField.getText());

            // Update the book's details
            selectedBook.setPrice(newPrice);
            selectedBook.setStock(newStock);

            // Update the book in the database
            bookManager.updateBook(selectedBook);

            showAlert("Success", "Product details updated successfully!");
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid values for price and stock.");
        }
    }

    @FXML
    private void handleBackAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) productNameComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not navigate back to the Admin Dashboard.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
