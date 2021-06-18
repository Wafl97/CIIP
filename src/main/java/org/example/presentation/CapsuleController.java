package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.example.logic.Domain;
import org.example.logic.DomainFacade;
import org.example.logic.Interfaces.IItem;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class CapsuleController extends App implements Initializable {

    @FXML
    private Button backButton, createButton, updateButton, browserButton, add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100;
    @FXML
    private TextField nameTextField, linkTextField;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private ImageView itemImageView;
    @FXML
    private ListView<IItem> containerListView;

    private File imageFile;

    private int itemIndex = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(e -> setRoot("main",Operation.PASS));

        createButton.setOnAction(e -> DOMAIN.getDomain().createContainer(CREATOR.emptyCapsule().populate(-1,
                priceSpinner.getValue(),
                nameTextField.getText(),
                imageFile.getName(),
                linkTextField.getText())));

        updateButton.setOnAction(e -> {
            //TODO
        });

        browserButton.setOnAction(e -> App.getInstance().openWeb("www.csgostash.com"));

        add0_25.setUserData(0.25d);      add0_5.setUserData(0.5d);  add1.setUserData(1.0d);     add10.setUserData(10.0d);       add100.setUserData(100.0d);
        lower0_25.setUserData(-0.25d);   lower0_5.setUserData(-0.5d); lower1.setUserData(-1.0d);  lower10.setUserData(-10.0d);    lower100.setUserData(-100.0d);

        containerListView.setCellFactory(cell -> new ListCell<>(){
            @Override
            protected void updateItem(IItem container, boolean bool){
                super.updateItem(container, bool);
                if (bool || container == null || container.getName() == null || DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()) == null){
                    setGraphic(null);
                    setText(null);
                }
                else {
                    setText(container.getName());
                    ImageView imageView = new ImageView(DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });

        containerListView.setItems(FXCollections.observableList(DOMAIN.getDomain().readAllContainers()));
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
            itemImageView.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(selectedContainer.getImage()));
            nameTextField.setText(selectedContainer.getName());
            priceSpinner.getValueFactory().setValue(selectedContainer.getInitPrice());
        }
    }

    @FXML
    public void imageHandler(ActionEvent event) throws URISyntaxException {
        FileChooser fileChooser = new FileChooser();
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null){
            DomainFacade.getInstance().getFileHandler().save(imageFile);
            itemImageView.setImage(DomainFacade.getInstance().getDataFacade().getGFX().getImageMap().get(imageFile.getName()));
        }
    }
}
