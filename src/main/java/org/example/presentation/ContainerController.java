package org.example.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.logic.Interfaces.Container;

import java.net.URL;
import java.util.ResourceBundle;

public class ContainerController extends App implements Initializable {

    @FXML
    private Button backButton, createButton, updateButton;
    @FXML
    private TextField nameTextField, priceTextField, imageTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(e -> setRoot("main",Operation.PASS));

        Container container = DOMAIN.readContainer(1);

        createButton.setOnAction(e -> DOMAIN.createContainer(CREATOR.emptyCapsule().populate(-1,
                Double.parseDouble(priceTextField.getText()),
                nameTextField.getText(),
                imageTextField.getText())));
        updateButton.setOnAction(e -> {
            container.setName(nameTextField.getText());
            container.setPrice(Double.parseDouble(priceTextField.getText()));
            DOMAIN.updateContainer(container.getId(),container);
        });
    }
}
