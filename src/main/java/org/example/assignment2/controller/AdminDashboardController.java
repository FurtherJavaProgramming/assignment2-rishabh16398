package org.example.assignment2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.BookManager;

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
        // Here you would open another window or prompt to update stock
        System.out.println("Update Stock button clicked!");
        // Add logic to open a dialog or a new scene for updating stock
    }

    @FXML
    private void handleLogout() {
        // Close the admin dashboard and return to the login or welcome page
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        // Optionally, open the login or welcome page here
        System.out.println("Admin logged out.");
    }
}
