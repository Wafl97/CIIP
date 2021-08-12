package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import org.example.logic.interfaces.dto.*;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.Identifiable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.util.Attributes.*;

public class ItemController extends App implements Initializable {

    @FXML
    private Button backButton, submitButton, browserButton, enableEditButton, deleteButton, chooseImageButton,
            add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100, updateCurrPriceButton;
    @FXML
    private RadioButton skinRadioButton, capsuleRadioButton, souvenirRadioButton, stickerRadioButton, patchRadioButton;
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
    @FXML
    private Label floatLabel, currPriceLabel;

    private File imageFile;

    private int itemIndex = -1;

    private Displayable loadedItem;

    private ToggleGroup radioToggle;

    private List<Node> skinProfile;

    private List<Node> nodeList;

    private boolean first = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nodeList = new ArrayList<>(Arrays.asList(submitButton, browserButton, deleteButton, chooseImageButton,
                add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100,
                skinRadioButton, capsuleRadioButton, souvenirRadioButton, stickerRadioButton, patchRadioButton,
                statTrackToggleButton, souvenirToggleButton,
                nameTextField, linkTextField, wearFloatTextField,
                floatLabel));

        skinProfile = new ArrayList<>(Arrays.asList(statTrackToggleButton,souvenirToggleButton,wearFloatTextField,floatLabel));
        skinProfileSetting(false,false);

        itemImageView.setPreserveRatio(true);
        itemImageView.setFitHeight(IMAGE_SIZE);

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
                    imageView.setFitHeight(IMAGE_ICON_SIZE);
                    setGraphic(imageView);
                }
            }
        });

        for (Displayable item : DOMAIN.readAllItems()){
            itemsListView.getItems().add(item);
        }
        itemsListView.setDisable(getOperation() == CREATE);
    }

    @FXML
    private void readItem(MouseEvent event){
        itemIndex = itemsListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            loadedItem = itemsListView.getSelectionModel().getSelectedItem();
            skinRadioButton.setSelected(loadedItem instanceof ISkin);
            capsuleRadioButton.setSelected(loadedItem instanceof ICapsule);
            souvenirRadioButton.setSelected(loadedItem instanceof ISouvenirCase);
            stickerRadioButton.setSelected(loadedItem instanceof ISticker);
            // TODO: 12-08-2021 do this
            itemImageView.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(loadedItem.getImage()));
            nameTextField.setText(loadedItem.getName());
            priceSpinner.getValueFactory().setValue(loadedItem.getInitPrice());
            linkTextField.setText(loadedItem.getStashLink());
            if (loadedItem.getStashLink() != null) {
                currPriceLabel.setText(String.valueOf(loadedItem.getCurrPrice()));
            }
            skinProfileSetting(loadedItem instanceof ISkin, getOperation().equals(Operation.EDIT) || getOperation().equals(Operation.CREATE));
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
        String name = (nameTextField.getText() == null || nameTextField.getText().equals("") ? loadedItem.getName() : nameTextField.getText());
        String imageName = (imageFile == null || imageFile.getName().equals("") ? getStringFromString(itemImageView.getImage().getUrl(), "/") : imageFile.getName());
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
            DOMAIN.getSkinDomain().updateSkin((ISkin) loadedItem);
        }
        else if (loadedItem instanceof ICapsule) {
            ((ICapsule) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getCapsuleDomain().updateCapsule((ICapsule) loadedItem);
        }
        else if (loadedItem instanceof ISouvenirCase) {
            ((ISouvenirCase) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getSouvenirCaseDomain().updateSouvenirCase((ISouvenirCase) loadedItem);
        }
        else if (loadedItem instanceof ISticker){
            ((ISticker) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getStickerDomain().updateSticker((ISticker) loadedItem);
        }
        else if (loadedItem instanceof IPatch){
            ((IPatch) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getPatchDomain().updatePatch((IPatch) loadedItem);
        }
        else if (loadedItem instanceof ICase){
            ((ICase) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getCaseDomain().updateCase((ICase) loadedItem);
        }
        else if (loadedItem instanceof ITicket){
            ((ITicket) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
        }
        else if (loadedItem instanceof IKey){
            ((IKey) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
        }
        else {
            throw new IllegalStateException("No Item type selected");
        }
    }

    private void createItem(){
        if (radioToggle.getSelectedToggle().getUserData() == SKIN){
            DOMAIN.getSkinDomain().createSkin(DOMAIN.getFactory().emptySkin().populate(
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
        else if (radioToggle.getSelectedToggle().getUserData() == CAPSULE){
            DOMAIN.getCapsuleDomain().createCapsule(DOMAIN.getFactory().emptyCapsule().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == SOUVENIR){
            DOMAIN.getSouvenirCaseDomain().createSouvenirCase(DOMAIN.getFactory().emptySouvenirCase().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == STICKER){
            DOMAIN.getStickerDomain().createSticker(DOMAIN.getFactory().emptySticker().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == PATCH){
            DOMAIN.getPatchDomain().createPatch(DOMAIN.getFactory().emptyPatch().populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        // TODO: 12-08-2021 do this
        else if (radioToggle.getSelectedToggle().getUserData() == null){
            throw new IllegalStateException("No Item type selected");
        }
    }

    private void deleteItem(){
        Identifiable item = itemsListView.getSelectionModel().getSelectedItem();
        if (item != null){
            if (item instanceof ISkin){
                DOMAIN.getSkinDomain().deleteSkin(item.getId());
            }
            else if (item instanceof ICapsule){
                DOMAIN.getCapsuleDomain().deleteCapsule(item.getId());
            }
            else if (item instanceof ISouvenirCase) {
                DOMAIN.getSouvenirCaseDomain().deleteSouvenirCase(item.getId());
            }
            else if(item instanceof ISticker){
                DOMAIN.getStickerDomain().deleteSticker(item.getId());
            }
            else if (item instanceof IPatch){
                DOMAIN.getPatchDomain().deletePatch(item.getId());
            }
            else if (item instanceof ICase){
                DOMAIN.getCaseDomain().deleteCase(item.getId());
            }
            else if (item instanceof ITicket){
                DOMAIN.getTicketDomain().deleteTicket(item.getId());
            }
            else if (item instanceof IKey){
                DOMAIN.getKeyDomain().deleteKey(item.getId());
            }
            itemsListView.getItems().remove(item);
        }
    }

    @FXML
    private void selectionHandler(ActionEvent event){
        RadioButton target = (RadioButton) event.getTarget();

        skinProfileSetting(target == skinRadioButton, getOperation().equals(Operation.EDIT) || getOperation().equals(Operation.CREATE));
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
                updateCurrPriceButton.setOnAction(e -> updateCurrPrice());
                enableEditButton.setDisable(true);
                deleteButton.setOnAction(e -> openWarning("Delete Item","Are you sure?","You are permanently deleting this item!",this::deleteItem,true));
                submitButton.setOnAction(e -> {
                    openWarning("Update Item","Are you sure?","You are changing date for this item!",this::updateItem,false);
                    setOperation(PASS);
                    buttonConfig(getOperation());
                });
                break;
            case PASS:
                enableEditButton.setOnAction(e -> enableEdit(true));
                updateCurrPriceButton.setOnAction(e -> updateCurrPrice());
                enableEdit(false);
                break;
            default:
                System.out.println("Something went wrong!!!");
                break;
        }
        if (first) {
            browserButton.setOnAction(e -> openWeb("www.csgostash.com"));
            backButton.setOnAction(e -> goBack());
            add0_25.setUserData(0.25d);
            add0_5.setUserData(0.5d);
            add1.setUserData(1.0d);
            add10.setUserData(10.0d);
            add100.setUserData(100.0d);
            lower0_25.setUserData(-0.25d);
            lower0_5.setUserData(-0.5d);
            lower1.setUserData(-1.0d);
            lower10.setUserData(-10.0d);
            lower100.setUserData(-100.0d);

            skinRadioButton.setUserData(SKIN);
            capsuleRadioButton.setUserData(CAPSULE);
            souvenirRadioButton.setUserData(SOUVENIR);
            stickerRadioButton.setUserData(STICKER);
            patchRadioButton.setUserData(PATCH);

            radioToggle = new ToggleGroup();
            skinRadioButton.setToggleGroup(radioToggle);
            capsuleRadioButton.setToggleGroup(radioToggle);
            souvenirRadioButton.setToggleGroup(radioToggle);
            stickerRadioButton.setToggleGroup(radioToggle);
            patchRadioButton.setToggleGroup(radioToggle);

            ToggleGroup toggle = new ToggleGroup();
            statTrackToggleButton.setToggleGroup(toggle);
            souvenirToggleButton.setToggleGroup(toggle);

            first = false;
        }
    }

    private void updateCurrPrice() {
        if (loadedItem != null){
            loadedItem.setPriceUpdated(false);
            currPriceLabel.setText(String.valueOf(loadedItem.getCurrPrice()));
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

    private void skinProfileSetting(boolean view, boolean enable){
        for (Node node : skinProfile) {
            node.setVisible(view);
            node.setDisable(!enable);
        }
    }

    private void enableEdit(boolean bool){
        enableEditButton.setDisable(bool);
        itemsListView.setDisable(bool);
        for (Node node : nodeList) {
            node.setDisable(!bool);
        }
        if (bool){
            setOperation(EDIT);
            buttonConfig(EDIT);
        }
    }
}
