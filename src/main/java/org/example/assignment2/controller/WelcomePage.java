package org.example.assignment2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePage {

    // Handles the Login button action
    @FXML
    private void onLoginAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Login.fxml"));
            Parent root = fxmlLoader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root, 600, 500));
            loginStage.setResizable(false);
            loginStage.show();

            // Close the current stage (Welcome page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles the Register button action
    @FXML
    private void onRegisterAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/Register.fxml"));
            Parent root = fxmlLoader.load();

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root, 600, 500));
            registerStage.setResizable(false);
            registerStage.show();

            // Close the current stage (Welcome page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles the Admin Login button action
    @FXML
    private void onAdminLoginAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/assignment2/fxml/AdminLogin.fxml"));
            Parent root = fxmlLoader.load();

            Stage adminStage = new Stage();
            adminStage.setTitle("Admin Login");
            adminStage.setScene(new Scene(root, 600, 500));
            adminStage.setResizable(false);
            adminStage.show();

            // Close the current stage (Welcome page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
