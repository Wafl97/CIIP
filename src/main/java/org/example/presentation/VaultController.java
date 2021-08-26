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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.IVault;
import org.example.logic.dto.interfaces.comps.Identifiable;

import static org.example.util.Attributes.*;

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
            DOMAIN.setSelectedVault((IVault) DOMAIN.getFactory().makeNew(VAULT));
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
            protected void updateItem(Displayable item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null || DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getName());
                    ImageView imageView = new ImageView(new Image(DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()).toURI().toString()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
        for (Identifiable item : DOMAIN.readAllItems()){
            Displayable displayable = (Displayable) item;
            allItemsListView.getItems().add(displayable);
        }
        itemsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Displayable item, boolean empty) {
                super.updateItem(item, empty);

                if (    empty ||
                        item == null ||
                        item.getName() == null ||
                        !tmpMap.containsKey(item) ||
                        DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(tmpMap.get(item) + "\t" + item.getName());
                    ImageView imageView = new ImageView(new Image(DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()).toURI().toString()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void createInvestment(){
        //===Name===
        IVault newInvestment = (IVault) DOMAIN.getFactory().makeNew(VAULT);
        newInvestment.populate(-1,nameTextField.getText());
        //===Content===
        newInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN.getVaultDomain().create(newInvestment);
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
        DOMAIN.getVaultDomain().update(loadedInvestment);
    }

    private void deleteInvestment(){
        if (loadedInvestment != null){
            long id = loadedInvestment.getId();
            DOMAIN.getVaultDomain().delete(id);
        }
    }
}