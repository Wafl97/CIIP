package org.example.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.ICapsule;
import org.example.logic.interfaces.ISkin;
import org.example.logic.interfaces.IVault;

public class VaultController extends App implements Initializable {

    @FXML
    private Button saveButton, backButton, deleteButton, addButton, removeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<Displayable> itemsListView;
    @FXML
    private ListView<Displayable> allItemsListView;
    @FXML
    private Spinner<Integer> amountSpinner;

    private Map<Displayable,Long> tmpMap;

    private IVault loadedInvestment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //===Operation==================================================================================================
        Operation operation = getOperation();
        if (operation == EDIT) readInvestment();
        else if (operation == CREATE){
            tmpMap = new HashMap<>();
            DOMAIN.setSelectedVault(DOMAIN.getFactory().emptyVault());
        }

        //===Buttons====================================================================================================
        backButton.setOnAction(e -> goBack());
        saveButton.setOnAction(e -> {
            if (operation == CREATE) createInvestment();
            else if (operation == EDIT) updateInvestment();
        });
        if (operation == CREATE) deleteButton.setDisable(true);
        else deleteButton.setOnAction(e -> openWarning("Delete Investment","Are you sure?","This Investment will be permanently removed!",this::deleteInvestment,true));
        addButton.setOnAction(e -> {
            if (allItemsListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0) {
                Displayable item = allItemsListView.getItems().get(allItemsListView.getSelectionModel().getSelectedIndex());
                long initialAmount = tmpMap.containsKey(item) ? tmpMap.get(item) : 0;
                tmpMap.put(item, initialAmount + (long) amountSpinner.getValue());
                if (!itemsListView.getItems().contains(item)) itemsListView.getItems().add(item);
                else itemsListView.refresh();
            }
        });
        removeButton.setOnAction(e -> {
            if (itemsListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0){
                Displayable item = itemsListView.getItems().get(itemsListView.getSelectionModel().getSelectedIndex());
                long amount = tmpMap.get(item);
                if (amount - amountSpinner.getValue() <= 0) {
                    tmpMap.remove(item);
                    itemsListView.getItems().remove(item);
                }
                else tmpMap.put(item, amount - amountSpinner.getValue());
                itemsListView.refresh();
            }
        });

        //===ListViews==================================================================================================
        allItemsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Displayable capsule, boolean empty) {
                super.updateItem(capsule, empty);

                if (empty || capsule == null || capsule.getName() == null) {
                    setText(null);
                } else {
                    setText(capsule.getName());
                }
            }
        });
        for (ICapsule capsule : DOMAIN.readAllCapsules()){
            allItemsListView.getItems().add(capsule);
        }
        for (ISkin skin : DOMAIN.readAllSkins()){
            allItemsListView.getItems().add(skin);
        }
        //allItemsListView.setItems(allCapsules);
        itemsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Displayable capsule, boolean empty) {
                super.updateItem(capsule, empty);

                if (    empty ||
                        capsule == null ||
                        capsule.getName() == null ||
                        !tmpMap.containsKey(capsule) ||
                        DOMAIN.getDataFacade().getGFX().getImageMap().get(capsule.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(tmpMap.get(capsule) + "\t" + capsule.getName());
                    ImageView imageView = new ImageView(DOMAIN.getDataFacade().getGFX().getImageMap().get(capsule.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void createInvestment(){
        //===Name===
        IVault newInvestment = DOMAIN.getFactory().emptyVault().populate(-1,nameTextField.getText());
        //===Content===
        newInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN.createVault(newInvestment);
    }

    private void readInvestment(){
        loadedInvestment = DOMAIN.getSelectedVault();
        //===Name===
        nameTextField.setText(loadedInvestment.getName());
        //===Content===
        ObservableList<Displayable> contentsContainers = FXCollections.observableList(new ArrayList<>(loadedInvestment.getItems()));
        itemsListView.setItems(contentsContainers);
        tmpMap = new HashMap<>(loadedInvestment.getAllItems());
    }

    private void updateInvestment(){
        //===Name===
        loadedInvestment.setName(nameTextField.getText());
        //===Content===
        loadedInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN.updateVault(loadedInvestment);
    }

    private void deleteInvestment(){
        if (loadedInvestment != null){
            long id = loadedInvestment.getId();
            DOMAIN.deleteVault(id);
        }
    }
}