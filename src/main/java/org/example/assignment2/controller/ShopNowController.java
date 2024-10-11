package org.example.assignment2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.BookManager;

import java.io.IOException;

public class ShopNowController {

    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Double> priceColumn;

    private ObservableList<Book> booksList;

    @FXML
    public void initialize() {
        // Initialize table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load books into the table
        booksList = FXCollections.observableArrayList(BookManager.getInstance().getAllBooks());
        booksTable.setItems(booksList);

        // Set up row click event to open BookDetails
        booksTable.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Book selectedBook = row.getItem();
                    showBookDetails(selectedBook);
                }
            });
            return row;
        });
    }

    // Method to open BookDetails view
    private void showBookDetails(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/BookDetails.fxml"));
            Parent root = loader.load();

            BookDetailsController bookDetailsController = loader.getController();
            bookDetailsController.setBookDetails(book);

            // Open the BookDetails in a new window
            Stage stage = new Stage();
            stage.setTitle("Book Details - " + book.getTitle());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh table data in case of stock updates
            refreshBooksTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to refresh the books table after any stock update
    private void refreshBooksTable() {
        booksList = FXCollections.observableArrayList(BookManager.getInstance().getAllBooks());
        booksTable.setItems(booksList);
    }

    // Method to display a confirmation message (used after adding to cart)
    public void displayConfirmationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleViewCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/ShoppingCart.fxml"));
            Parent root = loader.load();

            // Get the ShoppingCartController instance
            ShoppingCartController shoppingCartController = loader.getController();

            // Optionally, pass any necessary data to ShoppingCartController if needed

            // Set up the scene and stage for the shopping cart view
            Stage stage = (Stage) booksTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Shopping Cart");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBackToDashboardAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UserDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) booksTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
