module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens org.example.presentation to javafx.fxml;
    exports org.example.presentation;
}