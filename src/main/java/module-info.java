module org.example.assignment2 {
    // Required JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;

    // SQLite JDBC module
    requires org.xerial.sqlitejdbc;

    // Opens the main package for FXML reflection and access to other JavaFX elements
    opens org.example.assignment2 to javafx.fxml;

    // Export the main package if needed for testing or other purposes
    exports org.example.assignment2;

    // Export and open the controller package to javafx.fxml for FXML controllers
    exports org.example.assignment2.controller;
    opens org.example.assignment2.controller to javafx.fxml;

    // Open the model package to both Mockito (for testing) and FXML (if any FXML usage within models)
    opens org.example.assignment2.model to org.mockito, javafx.fxml;

    // Export the model package for potential test access
    exports org.example.assignment2.model;
}
