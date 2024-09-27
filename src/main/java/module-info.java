module org.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.assignment2 to javafx.fxml;
    exports org.example.assignment2;
}