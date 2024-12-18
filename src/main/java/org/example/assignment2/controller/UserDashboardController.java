package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.assignment2.model.Book;
import org.example.assignment2.model.BookManager;
import org.example.assignment2.model.UserManager;

import java.io.IOException;

public class UserDashboardController {

    @FXML
    private Text welcomeMessage;

    @FXML
    private TableView<Book> topBooksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Double> priceColumn;

    // Initialize method to set up columns and load data
    @FXML
    public void initialize() {
        // Set up the columns in the table
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); // Ensure this matches Book class attribute
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Disable resizing for all columns
        topBooksTable.getColumns().forEach(column -> column.setResizable(false));

        // Load book data using BookManager
        ObservableList<Book> topBooks = BookManager.getInstance().getTopBooks();
        topBooksTable.setItems(topBooks);
    }

    // Method to set welcome message after login
    public void setWelcomeMessage(String message) {
        welcomeMessage.setText(message);
    }

    @FXML
    private void handleViewProfile() {
        try {
            // Load the EditProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/EditProfile.fxml"));
            Parent root = loader.load();

            // Optionally, pass data to the EditProfileController if needed
            EditProfileController controller = loader.getController();
            // Set any initial data if needed, e.g., user details
            controller.initializeFields(UserManager.getInstance().getCurrentUser());

            // Get the current stage from the welcome message node
            Stage stage = (Stage) welcomeMessage.getScene().getWindow();

            // Set the new scene with the EditProfile.fxml content and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Edit Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewCart() {
        try {
            // Load the Cart.fxml file (Ensure this file exists in your resources)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/ShoppingCart.fxml"));
            Parent root = loader.load();

            // Get the current stage from the welcome message node
            Stage stage = (Stage) welcomeMessage.getScene().getWindow();

            // Set the new scene with the Cart.fxml content and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Shopping Cart");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewOrders() {
        try {
            // Load the OrderHistory.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/OrderHistory.fxml"));
            Parent root = loader.load();

            // Get the current stage from the welcome message node
            Stage stage = (Stage) welcomeMessage.getScene().getWindow();

            // Set the new scene with the OrderHistory.fxml content and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Order History");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //User Logout Handler
    @FXML
    private void handleLogout() {
        try {
            // Load the WelcomePage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Welcome.fxml"));
            Parent root = loader.load();

            // Get the current stage from the welcome message node
            Stage stage = (Stage) welcomeMessage.getScene().getWindow();

            // Set the new scene with the WelcomePage.fxml content and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();

            System.out.println("User logged out.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleShopNow() {
        try {
            // Load the ShopNow.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/ShopNow.fxml"));
            Parent root = loader.load();

            // Get the current stage from the welcome message node
            Stage stage = (Stage) welcomeMessage.getScene().getWindow();

            // Set the new scene with the ShopNow.fxml content and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Shop Now");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
