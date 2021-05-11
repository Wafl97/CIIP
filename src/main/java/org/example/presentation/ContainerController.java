package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.logic.Interfaces.Container;

import java.net.URL;
import java.util.ResourceBundle;

public class ContainerController extends App implements Initializable {

    @FXML
    private Button backButton, createButton, updateButton, add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100;
    @FXML
    private TextField nameTextField, imageTextField;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private ImageView itemImageView;
    @FXML
    private ListView<Container> containerListView;

    private int itemIndex = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(e -> setRoot("main",Operation.PASS));

        createButton.setOnAction(e -> DOMAIN.createContainer(CREATOR.emptyCapsule().populate(-1,
                priceSpinner.getValue(),
                nameTextField.getText(),
                imageTextField.getText())));
        updateButton.setOnAction(e -> {
            //TODO
        });
        add0_25.setUserData(0.25d);      add0_5.setUserData(0.5d);  add1.setUserData(1.0d);     add10.setUserData(10.0d);       add100.setUserData(100.0d);
        lower0_25.setUserData(0.25d);   lower0_5.setUserData(0.5d); lower1.setUserData(-1.0d);  lower10.setUserData(-10.0d);    lower100.setUserData(-100.0d);

        containerListView.setCellFactory(cell -> new ListCell<>(){
            @Override
            protected void updateItem(Container container, boolean bool){
                super.updateItem(container, bool);
                if (bool || container == null || container.getName() == null || DOMAIN.getGFX().getImageMap().get(container.getImage()) == null){
                    setGraphic(null);
                    setText(null);
                }
                else {
                    setText(container.getName());
                    ImageView imageView = new ImageView(DOMAIN.getGFX().getImageMap().get(container.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });

        containerListView.setItems(FXCollections.observableList(DOMAIN.readAllContainers()));
        containerListView.refresh();
    }

    @FXML
    private void addToPriceSpinner(ActionEvent event){
        double value = (double) ((Button) event.getTarget()).getUserData();
        if (priceSpinner.getValue() + value < 0) {
            priceSpinner.getValueFactory().setValue(0.0d);
        } else {
            priceSpinner.getValueFactory().setValue(priceSpinner.getValue() + value);
        }
    }

    @FXML
    public void readItem(MouseEvent event){
        itemIndex = containerListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            selectedContainer = containerListView.getSelectionModel().getSelectedItem();
            itemImageView.setImage(DOMAIN.getGFX().getImageMap().get(selectedContainer.getImage()));
            nameTextField.setText(selectedContainer.getName());
            priceSpinner.getValueFactory().setValue(selectedContainer.getPrice());
        }
    }
}
