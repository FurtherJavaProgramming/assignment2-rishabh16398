package org.example.assignment2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assignment2.model.UserManager;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorMessage;

    @FXML
    private void handleRegisterAction(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            errorMessage.setText("Passwords do not match.");
            errorMessage.setVisible(true);
            return;
        }

        // Register the user (without email validation)
        boolean success = UserManager.getInstance().registerUser(firstName, lastName, username, password, false);

        if (success) {
            // If registration is successful, move to the login screen
            moveToLoginScreen(event, "User successfully registered.");
        } else {
            errorMessage.setText("Username is already taken.");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        try {
            // Load the Welcome.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Welcome.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene with Welcome.fxml and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveToLoginScreen(ActionEvent event, String successMessage) {
        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Login.fxml"));
            Parent root = loader.load();

            // Pass success message to login controller
            LoginController loginController = loader.getController();
            loginController.displaySuccessMessage(successMessage);

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene with Login.fxml and show the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
