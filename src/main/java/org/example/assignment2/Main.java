package org.example.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assignment2.util.DataInitializer;
import org.example.assignment2.util.DatabaseInitializer;

import java.io.IOException;
import java.sql.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 628, 462);
        stage.setTitle("Welcome Page");
        stage.setScene(scene);

        // Disable window resizing
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}
