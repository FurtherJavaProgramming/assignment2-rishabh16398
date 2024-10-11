package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assignment2.model.Admin;
import org.example.assignment2.model.AdminManager;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Use lambda for the button's onAction
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Authenticate the admin using AdminManager
        Admin admin = AdminManager.getInstance().authenticateAdmin(username, password);
        if (admin != null && admin.isAdmin()) {
            openAdminDashboard();
        } else {
            errorLabel.setText("Invalid username or password");
            errorLabel.setVisible(true);
        }
    }

    private void openAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToWelcome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Welcome.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found.");
            }

            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome");
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load the Welcome Page.");
            errorLabel.setVisible(true);
        }
    }
}
