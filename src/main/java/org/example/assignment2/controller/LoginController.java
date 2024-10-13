package org.example.assignment2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.assignment2.model.User;
import org.example.assignment2.model.UserManager;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label successMessage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    // Handle Submit Button action for login
    @FXML
    private void handleSubmitAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Use UserManager to authenticate
        UserManager userManager = UserManager.getInstance();
        User user = userManager.login(username, password);

        if (user != null) {
            // Move to the user dashboard
            moveToUserDashboard(event, user.getFirstName());
        } else {
            // Display error message
            errorMessage.setText("Invalid username or password.");
            errorMessage.setVisible(true);
        }
    }


    // Method to display the success message
    public void displaySuccessMessage(String message) {
        if (message != null && !message.isEmpty()) {
            successMessage.setText(message);
            successMessage.setStyle("-fx-text-fill: green;");
            successMessage.setVisible(true);
        }
    }

    // Method to load the UserDashboard scene
    private void moveToUserDashboard(ActionEvent event, String firstName) {
        try {
            // Load the UserDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UserDashboard.fxml"));
            Parent root = loader.load();

            // Get the UserDashboardController to pass the username
            UserDashboardController dashboardController = loader.getController();
            dashboardController.setWelcomeMessage("Welcome, " + firstName + "!");

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene with UserDashboard.fxml and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("User Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Cancel Button action
    @FXML
    private void handleCancelAction(ActionEvent event) throws IOException {
        // Load the Welcome.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Welcome.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the welcome scene on it
        Stage stage = (Stage) successMessage.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Welcome Page");

    }

    // Handle Register Button action
    @FXML
    private void handleRegisterAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
