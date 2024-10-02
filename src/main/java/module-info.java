module org.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    opens org.example.assignment2 to javafx.fxml;
    exports org.example.assignment2;
    exports org.example.assignment2.controller;
    opens org.example.assignment2.controller to javafx.fxml;
}