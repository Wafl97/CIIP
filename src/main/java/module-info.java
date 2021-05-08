module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.presentation to javafx.fxml;
    exports org.example.presentation;
}