package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.CartManager;

import java.io.IOException;

public class BookDetailsController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Spinner<Integer> quantitySpinner;

    private Book book;

    // Set book details on the view
    public void setBookDetails(Book book) {
        this.book = book;
        titleLabel.setText(book.getTitle());
        authorLabel.setText("Author: " + book.getAuthors());
        priceLabel.setText("Price: $" + book.getPrice());

        // Set up quantity spinner with a range from 1 to available stock
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, book.getStock(), 1);
        quantitySpinner.setValueFactory(valueFactory);
    }

    @FXML
    private void handleAddToCart() {
        int quantity = quantitySpinner.getValue();
        CartManager.getInstance().addToCart(book, quantity);

        // Show confirmation message
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Book Added to Cart");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(book.getTitle() + " has been successfully added to your cart.");
        confirmationAlert.showAndWait();

        // Close this BookDetails window to return to ShopNow
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Close the BookDetails window
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

}
