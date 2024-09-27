package org.example.assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;

import java.io.IOException;

public class WelcomePage {

    @FXML
    private Button loginButton;

    // This method will be called when the login button is clicked
    @FXML
    public void onLoginAction(ActionEvent event) {
        try {
            // Load the new FXML for the login window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage (window) and set the scene
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root, 600, 500)); // Set the size of the window

            // Make the new window non-resizable
            loginStage.setResizable(false);

            // Show the new window
            loginStage.show();

            // Close the current Welcome window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
