package org.example.assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WelcomePage {

    @FXML
    private Button loginButton;

    public void onLoginAction(ActionEvent event){
        System.out.println("Hello");
    }
}