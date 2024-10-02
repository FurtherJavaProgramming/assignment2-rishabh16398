package org.example.assignment2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleSubmitAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Basic validation
        if (username.equals("admin") && password.equals("password")) {
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    public void handleRegisterAction(ActionEvent event) {
        // Handle register action here
        showAlert(AlertType.INFORMATION, "Register", "Registration process initiated.");
        // You can open a registration window or navigate to a registration scene here
    }

    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
