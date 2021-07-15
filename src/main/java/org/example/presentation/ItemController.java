package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import org.example.logic.interfaces.ISouvenirCase;
import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.ICapsule;
import org.example.logic.interfaces.ISkin;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemController extends App implements Initializable {

    @FXML
    private Button backButton, submitButton, browserButton, enableEditButton, deleteButton, chooseImageButton,
            add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100;
    @FXML
    private RadioButton skinRadioButton, capsuleRadioButton, souvenirRadioButton, stickerRadioButton;
    @FXML
    private ToggleButton statTrackToggleButton, souvenirToggleButton;
    @FXML
    private TextField nameTextField, linkTextField, wearFloatTextField;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private ImageView itemImageView;
    @FXML
    private ListView<Displayable> itemsListView;

    private File imageFile;

    private int itemIndex = -1;

    private Displayable loadedItem;

    private ViewProfile skinProfile;

    private ToggleGroup radioToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        skinProfile = new ViewProfile();
        skinProfile.addToggleButton(statTrackToggleButton);
        skinProfile.addToggleButton(souvenirToggleButton);
        skinProfile.addTextField(wearFloatTextField);
        skinProfile.use(false);

        // FIXME: 04-07-2021
        deleteButton.setOnAction(e -> DOMAIN.deleteCapsule(1));
        buttonConfig(getOperation());

        itemsListView.setCellFactory(cell -> new ListCell<>(){
            @Override
            protected void updateItem(Displayable capsule, boolean bool){
                super.updateItem(capsule, bool);
                if (bool || capsule == null || capsule.getName() == null || DOMAIN.getDataFacade().getGFX().getImageMap().get(capsule.getImage()) == null){
                    setGraphic(null);
                    setText(null);
                }
                else {
                    setText(capsule.getName());
                    ImageView imageView = new ImageView(DOMAIN.getDataFacade().getGFX().getImageMap().get(capsule.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });

        for (Displayable item : DOMAIN.readAllItems()){
            itemsListView.getItems().add(item);
        }
        itemsListView.refresh();
    }

    @FXML
    private void readItem(MouseEvent event){
        itemIndex = itemsListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            loadedItem = itemsListView.getSelectionModel().getSelectedItem();
            itemImageView.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(loadedItem.getImage()));
            nameTextField.setText(loadedItem.getName());
            priceSpinner.getValueFactory().setValue(loadedItem.getInitPrice());
            linkTextField.setText(loadedItem.getStashLink());
            skinProfile.use(loadedItem instanceof ISkin);
            if (loadedItem instanceof ISkin){
                wearFloatTextField.setText(String.valueOf(((ISkin) loadedItem).getWearFloat()));
                statTrackToggleButton.setSelected(((ISkin) loadedItem).isStatTrak());
                souvenirToggleButton.setSelected(((ISkin) loadedItem).isSouvenir());
            }
        }
    }

    private void updateItem(){
        long id = loadedItem.getId();
        double initPrice = (priceSpinner.getValue() == null || priceSpinner.getValue() == 0.0 ? loadedItem.getInitPrice() : priceSpinner.getValue());
        String name = (nameTextField.getText() == null || nameTextField.getText().equals("")? loadedItem.getName() : nameTextField.getText());
        String imageName = (imageFile == null || imageFile.getName().equals("") ? getStringFromString(itemImageView.getImage().getUrl(),"/") : imageFile.getName());
        String link = (linkTextField.getText() == null || linkTextField.getText().equals("") ? loadedItem.getStashLink() : linkTextField.getText());
        if (loadedItem instanceof ISkin) {
            double wearFloat = Double.parseDouble(wearFloatTextField.getText());
            boolean statTrack = statTrackToggleButton.isSelected();
            boolean souvenir = souvenirRadioButton.isSelected();
            ((ISkin) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link,
                    wearFloat,
                    statTrack,
                    souvenir
            );
            DOMAIN.updateSkin((ISkin) loadedItem);
        }
        else if (loadedItem instanceof ICapsule){
            ((ICapsule) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.updateCapsule((ICapsule) loadedItem);
        }
        else if (loadedItem instanceof ISouvenirCase){
            ((ISouvenirCase) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.updateSouvenirCase((ISouvenirCase) loadedItem);
        }
    }

    private void createItem(){
        if (radioToggle.getUserData() instanceof ISkin){
            DOMAIN.createSkin(DOMAIN.getFactory().emptySkin().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText(),
                    Double.parseDouble(wearFloatTextField.getText()),
                    statTrackToggleButton.isSelected(),
                    souvenirRadioButton.isSelected()
            ));
        }
        else if (radioToggle.getUserData() instanceof ICapsule){
            DOMAIN.createCapsule(DOMAIN.getFactory().emptyCapsule().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getUserData() instanceof ISouvenirCase){
            DOMAIN.createSouvenirCase(DOMAIN.getFactory().emptySouvenirCase().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }

    }

    @FXML
    private void selectionHandler(ActionEvent event){
        RadioButton target = (RadioButton) event.getTarget();

        skinProfile.use(target == skinRadioButton);
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
    public void imageHandler(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null){
            DOMAIN.getFileHandler().save(imageFile);
            itemImageView.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(imageFile.getName()));
        }
    }

    private void buttonConfig(Operation operation){
        switch (operation){
            case CREATE:
                enableEditButton.setDisable(true);
                submitButton.setOnAction(e -> openWarning("Create Item","Are you sure?","You are creating a new item",this::createItem,true));
                break;
            case EDIT:
                enableEditButton.setDisable(true);
                submitButton.setOnAction(e -> openWarning("Update Item","Are you sure?","You are changing date for this item!",this::updateItem,true));
                break;
            case PASS:
                enableEditButton.setOnAction(e -> enableEdit(true));
                enableEdit(false);
                break;
            default:
                System.out.println("Something went wrong!!!");
                break;
        }
        browserButton.setOnAction(e -> openWeb("www.csgostash.com"));
        backButton.setOnAction(e -> goBack());
        add0_25.setUserData(0.25d);      add0_5.setUserData(0.5d);  add1.setUserData(1.0d);     add10.setUserData(10.0d);       add100.setUserData(100.0d);
        lower0_25.setUserData(-0.25d);   lower0_5.setUserData(-0.5d); lower1.setUserData(-1.0d);  lower10.setUserData(-10.0d);    lower100.setUserData(-100.0d);

        radioToggle = new ToggleGroup();
        skinRadioButton.setToggleGroup(radioToggle);
        capsuleRadioButton.setToggleGroup(radioToggle);
        souvenirRadioButton.setToggleGroup(radioToggle);
        stickerRadioButton.setToggleGroup(radioToggle);

        ToggleGroup toggle = new ToggleGroup();
        statTrackToggleButton.setToggleGroup(toggle);
        souvenirToggleButton.setToggleGroup(toggle);
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
        chooseImageButton.setDisable(b);
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
        if (bool) buttonConfig(EDIT);
    }
}
