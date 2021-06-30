package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.example.logic.DomainFacade;
import org.example.logic.Interfaces.IItem;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CapsuleController extends App implements Initializable {

    @FXML
    private Button backButton, submitButton, browserButton, enableEditButton,
            add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100;
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

    private IItem loadedItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(e -> App.getInstance().goBack());

        switch (App.getInstance().getOperation()){
            case CREATE:
                enableEditButton.setDisable(true);
                submitButton.setOnAction(e -> App.getInstance().openWarning("Create Item","Are you sure?","You are creating a new item",this::createItem,true));
                break;
            case EDIT:
                enableEditButton.setDisable(true);
                submitButton.setOnAction(e -> App.getInstance().openWarning("Update Item","Are you sure?","You are changing date for this item!",this::updateItem,true));
                                break;
            case PASS:
                enableEditButton.setOnAction(e -> enableEdit(true));
                enableEdit(false);
                break;
            default:
                System.out.println("Something went wrong!!!");
                break;
        }
        System.out.println(getOperation());

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
    private void readItem(MouseEvent event){
        itemIndex = containerListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            loadedItem = containerListView.getSelectionModel().getSelectedItem();
            itemImageView.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(loadedItem.getImage()));
            nameTextField.setText(loadedItem.getName());
            priceSpinner.getValueFactory().setValue(loadedItem.getInitPrice());
            linkTextField.setText(loadedItem.getStashLink());
        }
    }

    private void updateItem(){
        long id = loadedItem.getId();
        double initPrice = (priceSpinner.getValue() == null || priceSpinner.getValue() == 0.0 ? loadedItem.getInitPrice() : priceSpinner.getValue());
        String name = (nameTextField.getText() == null || nameTextField.getText().equals("")? loadedItem.getName() : nameTextField.getText());
        String imageName = (imageFile == null || imageFile.getName().equals("") ? getStringFromString(itemImageView.getImage().getUrl(),"/") : imageFile.getName());
        String link = (linkTextField.getText() == null || linkTextField.getText().equals("") ? loadedItem.getStashLink() : linkTextField.getText());
        loadedItem.populate(
                id,
                initPrice,
                name,
                imageName,
                link
        );
        DOMAIN.getDomain().updateContainer(id,getSelectedItem());
    }

    private void createItem(){
        DOMAIN.getDomain().createContainer(CREATOR.emptyCapsule().populate(
                -1,
                priceSpinner.getValue(),
                nameTextField.getText(),
                imageFile.getName(),
                linkTextField.getText()));
    }

    @FXML
    public void imageHandler(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null){
            DomainFacade.getInstance().getFileHandler().save(imageFile);
            itemImageView.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(imageFile.getName()));
        }
    }

    /**
     * Helper methode for getting a String from the end of an other String
     * @param string the input being split
     * @param regex the string used for splitting
     * @return the end of the string
     */
    private String getStringFromString(String string, String regex){
        String[] tmp = string.split(regex);
        return tmp[tmp.length-1];
    }

    private void enableEdit(boolean bool){
        boolean b = !bool;
        enableEditButton.setDisable(bool);
        submitButton.setDisable(b);
        linkTextField.setDisable(b);
        nameTextField.setDisable(b);
        priceSpinner.setDisable(b);
        submitButton.setDisable(b);
        browserButton.setDisable(b);
        add0_25.setDisable(b);
        lower0_25.setDisable(b);
        add0_5.setDisable(b);
        lower0_5.setDisable(b);
        add1.setDisable(b);
        lower1.setDisable(b);
        add10.setDisable(b);
        lower10.setDisable(b);
        add100.setDisable(b);
        lower100.setDisable(b);
    }
}
