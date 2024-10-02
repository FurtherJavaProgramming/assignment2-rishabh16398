module org.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;

    // Add opens for reflection and mocking
    opens org.example.assignment2 to javafx.fxml;
    exports org.example.assignment2;

    // Controller needs to be accessible by FXML
    exports org.example.assignment2.controller;
    opens org.example.assignment2.controller to javafx.fxml;

    // Allow Mockito to access the model package for testing purposes
    opens org.example.assignment2.model to org.mockito, javafx.fxml;

    // You may also need to export the model package if you're using it in tests
    exports org.example.assignment2.model;
}
