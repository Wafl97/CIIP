package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import org.example.logic.dto.interfaces.*;
import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.util.Attributes;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.util.Attributes.*;

public class ItemController extends App implements Initializable {

    @FXML
    private Button  backButton, submitButton, browserButton,
                    enableEditButton, deleteButton, chooseImageButton, updateCurrPriceButton,
                    add0_25, lower0_25, add0_5, lower0_5, add1, lower1, add10, lower10, add100, lower100;
    @FXML
    private RadioButton skinRadioButton, capsuleRadioButton, souvenirRadioButton,
                        stickerRadioButton, patchRadioButton, caseRadioButton,
                        ticketRadioButton, keyRadioButton, musicRadioButton,
                        pinRadioButton, modelRadioButton, graffitiRadioButton;
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
                skinRadioButton, capsuleRadioButton, souvenirRadioButton, stickerRadioButton, patchRadioButton, caseRadioButton,
                ticketRadioButton, keyRadioButton, musicRadioButton,
                pinRadioButton, modelRadioButton, graffitiRadioButton,
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

        for (Identifiable item : DOMAIN.readAllItems()){
            Displayable displayable = (Displayable) item;
            itemsListView.getItems().add(displayable);
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
            caseRadioButton.setSelected(loadedItem instanceof ICase);
            ticketRadioButton.setSelected(loadedItem instanceof ITicket);
            keyRadioButton.setSelected(loadedItem instanceof IKey);
            musicRadioButton.setSelected(loadedItem instanceof IMusicKit);
            pinRadioButton.setSelected(loadedItem instanceof IPin);
            modelRadioButton.setSelected(loadedItem instanceof IPlayerModel);
            graffitiRadioButton.setSelected(loadedItem instanceof IGraffiti);
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
            DOMAIN.getSkinDomain().update((ISkin) loadedItem);
        }
        else if (loadedItem instanceof ICapsule) {
            ((ICapsule) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getCapsuleDomain().update((ICapsule) loadedItem);
        }
        else if (loadedItem instanceof ISouvenirCase) {
            ((ISouvenirCase) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getSouvenirCaseDomain().update((ISouvenirCase) loadedItem);
        }
        else if (loadedItem instanceof ISticker){
            ((ISticker) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getStickerDomain().update((ISticker) loadedItem);
        }
        else if (loadedItem instanceof IPatch){
            ((IPatch) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getPatchDomain().update((IPatch) loadedItem);
        }
        else if (loadedItem instanceof ICase){
            ((ICase) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getCaseDomain().update((ICase) loadedItem);
        }
        else if (loadedItem instanceof ITicket){
            ((ITicket) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getTicketDomain().update((ITicket) loadedItem);
        }
        else if (loadedItem instanceof IKey){
            ((IKey) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getKeyDomain().update((IKey) loadedItem);
        }
        else if (loadedItem instanceof IMusicKit){
            ((IMusicKit) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getMusicKitDomain().update((IMusicKit) loadedItem);
        }
        else if (loadedItem instanceof IPin){
            ((IPin) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getPinDomain().update((IPin) loadedItem);
        }
        else if (loadedItem instanceof IPlayerModel){
            ((IPlayerModel) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getPlayerModelDomain().update((IPlayerModel) loadedItem);
        }
        else if (loadedItem instanceof IGraffiti){
            ((IGraffiti) loadedItem).populate(
                    id,
                    initPrice,
                    name,
                    imageName,
                    link
            );
            DOMAIN.getGraffitiDomain().update((IGraffiti) loadedItem);
        }
        else {
            throw new IllegalStateException("No Item type selected");
        }
    }

    private void createItem(){
        Convertible item = DOMAIN.getFactory().makeNew((Attributes) radioToggle.getSelectedToggle().getUserData());
        if (radioToggle.getSelectedToggle().getUserData() == SKIN){
            ISkin s = (ISkin) item;
            DOMAIN.getSkinDomain().create(s.populate(
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
            ICapsule c = (ICapsule) item;
            DOMAIN.getCapsuleDomain().create(c.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == SOUVENIR){
            ISouvenirCase s = (ISouvenirCase) item;
            DOMAIN.getSouvenirCaseDomain().create(s.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == STICKER){
            ISticker s = (ISticker) item;
            DOMAIN.getStickerDomain().create(s.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == PATCH){
            IPatch p = (IPatch) item;
            DOMAIN.getPatchDomain().create(p.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == CASE){
            ICase c = (ICase) item;
            DOMAIN.getCaseDomain().create(c.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == TICKET){
            ITicket t = (ITicket) item;
            DOMAIN.getTicketDomain().create(t.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == KEY){
            IKey k = (IKey) item;
            DOMAIN.getKeyDomain().create(k.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == MUSICKIT){
            IMusicKit m = (IMusicKit) item;
            DOMAIN.getMusicKitDomain().create(m.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == PIN){
            IPin p = (IPin) item;
            DOMAIN.getPinDomain().create(p.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == PLAYERMODEL){
            IPlayerModel p = (IPlayerModel) item;
            DOMAIN.getPlayerModelDomain().create(p.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        else if (radioToggle.getSelectedToggle().getUserData() == GRAFFITI){
            IGraffiti g = (IGraffiti) item;
            DOMAIN.getGraffitiDomain().create(g.populate(
                    -1,
                    priceSpinner.getValue(),
                    nameTextField.getText(),
                    imageFile.getName(),
                    linkTextField.getText()
            ));
        }
        // TODO: 12-08-2021 do this, make a switch case (maybe a choiceBox)
        else if (radioToggle.getSelectedToggle().getUserData() == null){
            throw new IllegalStateException("No Item type selected");
        }
    }

    private void deleteItem(){
        Identifiable item = itemsListView.getSelectionModel().getSelectedItem();
        if (item != null){
            if (item instanceof ISkin){
                DOMAIN.getSkinDomain().delete(item.getId());
            }
            else if (item instanceof ICapsule){
                DOMAIN.getCapsuleDomain().delete(item.getId());
            }
            else if (item instanceof ISouvenirCase) {
                DOMAIN.getSouvenirCaseDomain().delete(item.getId());
            }
            else if(item instanceof ISticker){
                DOMAIN.getStickerDomain().delete(item.getId());
            }
            else if (item instanceof IPatch){
                DOMAIN.getPatchDomain().delete(item.getId());
            }
            else if (item instanceof ICase){
                DOMAIN.getCaseDomain().delete(item.getId());
            }
            else if (item instanceof ITicket){
                DOMAIN.getTicketDomain().delete(item.getId());
            }
            else if (item instanceof IKey){
                DOMAIN.getKeyDomain().delete(item.getId());
            }
            else if (item instanceof IMusicKit){
                DOMAIN.getMusicKitDomain().delete(item.getId());
            }
            else if (item instanceof IPin){
                DOMAIN.getPinDomain().delete(item.getId());
            }
            else if (item instanceof IPlayerModel){
                DOMAIN.getPlayerModelDomain().delete(item.getId());
            }
            else if (item instanceof IGraffiti){
                DOMAIN.getGraffitiDomain().delete(item.getId());
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
            caseRadioButton.setUserData(CASE);
            ticketRadioButton.setUserData(TICKET);
            keyRadioButton.setUserData(KEY);
            musicRadioButton.setUserData(MUSICKIT);
            pinRadioButton.setUserData(PIN);
            modelRadioButton.setUserData(PLAYERMODEL);
            graffitiRadioButton.setUserData(GRAFFITI);

            radioToggle = new ToggleGroup();
            skinRadioButton.setToggleGroup(radioToggle);
            capsuleRadioButton.setToggleGroup(radioToggle);
            souvenirRadioButton.setToggleGroup(radioToggle);
            stickerRadioButton.setToggleGroup(radioToggle);
            patchRadioButton.setToggleGroup(radioToggle);
            caseRadioButton.setToggleGroup(radioToggle);
            ticketRadioButton.setToggleGroup(radioToggle);
            keyRadioButton.setToggleGroup(radioToggle);
            musicRadioButton.setToggleGroup(radioToggle);
            pinRadioButton.setToggleGroup(radioToggle);
            modelRadioButton.setToggleGroup(radioToggle);
            graffitiRadioButton.setToggleGroup(radioToggle);

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
