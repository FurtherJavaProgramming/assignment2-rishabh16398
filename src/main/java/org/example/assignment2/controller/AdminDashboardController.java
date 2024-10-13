package org.example.assignment2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.BookManager;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private Text welcomeText;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorsColumn;

    @FXML
    private TableColumn<Book, Double> priceColumn;

    @FXML
    private TableColumn<Book, Integer> soldCopiesColumn;

    @FXML
    private TableColumn<Book, Integer> stockColumn;

    @FXML
    private Button updateStockButton;

    @FXML
    private Button logoutButton;

    private final BookManager bookManager = BookManager.getInstance();

    @FXML
    private void initialize() {
        // Set the TableView to non-editable
        booksTable.setEditable(false);

        // Set up table columns (non-editable and non-resizable)
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setResizable(false);

        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        authorsColumn.setResizable(false);

        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setResizable(false);

        soldCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("soldCopies"));
        soldCopiesColumn.setResizable(false);

        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setResizable(false);

        // Load books into the table
        loadBooks();

        // Setup actions for buttons
        updateStockButton.setOnAction(event -> handleUpdateStock());
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void loadBooks() {
        ObservableList<Book> booksList = FXCollections.observableArrayList(bookManager.getAllBooks());
        booksTable.setItems(booksList);
    }

    private void handleUpdateStock() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UpdateProduct.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) updateStockButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            // Load the Welcome.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Welcome.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the welcome scene on it
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome");

            // Optionally, clear any session-specific data if needed here
            System.out.println("Admin logged out.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
