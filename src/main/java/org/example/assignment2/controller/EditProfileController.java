package org.example.assignment2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import org.example.assignment2.model.UserManager;
import org.example.assignment2.model.User;

import java.io.IOException;

public class EditProfileController {

    @FXML
    private Label statusMessage;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    private User currentUser;

    // Method to initialize the controller with the current user data
    public void initializeFields(User user) {
        this.currentUser = user;
        if (user != null) {
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            passwordField.setText("");  // Leave password field empty for user input
        }
    }

    // Handle Save Changes action
    @FXML
    private void handleSaveChanges() {
        String newFirstName = firstNameField.getText();
        String newLastName = lastNameField.getText();
        String newPassword = passwordField.getText();

        boolean updateSuccess = UserManager.getInstance().updateUser(
                currentUser.getUsername(),
                newFirstName.isEmpty() ? currentUser.getFirstName() : newFirstName,
                newLastName.isEmpty() ? currentUser.getLastName() : newLastName,
                newPassword.isEmpty() ? currentUser.getPassword() : newPassword
        );

        if (updateSuccess) {
            statusMessage.setText("Profile updated successfully.");
            statusMessage.setStyle("-fx-text-fill: green;");
        } else {
            statusMessage.setText("Failed to update profile.");
            statusMessage.setStyle("-fx-text-fill: red;");
        }
    }

    // Handle Back action
    @FXML
    private void handleBackAction(ActionEvent event) {
        try {
            // Load the UserDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/UserDashboard.fxml"));
            Parent root = loader.load();

            // Set the new scene to the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("User Dashboard");
            stage.show();

            // Optionally, pass the user data back to the UserDashboardController
            UserDashboardController dashboardController = loader.getController();
            dashboardController.setWelcomeMessage("Welcome, " + currentUser.getFirstName() + "!");  // Optional: Set a welcome message with the user's first name
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
