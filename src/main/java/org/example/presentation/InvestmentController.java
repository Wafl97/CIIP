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

import org.example.logic.Interfaces.ICapsule;
import org.example.logic.Interfaces.IVault;

public class InvestmentController extends App implements Initializable {

    @FXML
    private Button saveButton, backButton, addButton, removeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<ICapsule> contentsListView;
    @FXML
    private ListView<ICapsule> allContainersListView;
    @FXML
    private Spinner<Integer> amountSpinner;

    private Map<ICapsule,Long> tmpMap;

    private IVault loadedInvestment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //===Operation==================================================================================================
        Operation operation = getOperation();
        if (operation == EDIT) readInvestment();
        else if (operation == CREATE){
            tmpMap = new HashMap<>();
            DOMAIN_FACADE.setSelectedInvestment(DOMAIN_FACADE.getFactory().emptyVault());
        }

        //===Buttons====================================================================================================
        backButton.setOnAction(e -> goBack());
        saveButton.setOnAction(e -> {
            if (operation == CREATE) createInvestment();
            else if (operation == EDIT) updateInvestment();
        });
        addButton.setOnAction(e -> {
            if (allContainersListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0) {
                ICapsule capsule = allContainersListView.getItems().get(allContainersListView.getSelectionModel().getSelectedIndex());
                long initialAmount = tmpMap.containsKey(capsule) ? tmpMap.get(capsule) : 0;
                tmpMap.put(capsule, initialAmount + (long) amountSpinner.getValue());
                if (!contentsListView.getItems().contains(capsule)) contentsListView.getItems().add(capsule);
                else contentsListView.refresh();
            }
        });
        removeButton.setOnAction(e -> {
            if (contentsListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0){
                ICapsule capsule = contentsListView.getItems().get(contentsListView.getSelectionModel().getSelectedIndex());
                long amount = tmpMap.get(capsule);
                if (amount + amountSpinner.getValue() <= 0) contentsListView.getItems().remove(capsule);
                else tmpMap.put(capsule, amount - amountSpinner.getValue());
                contentsListView.refresh();
            }
        });

        //===ListViews==================================================================================================
        allContainersListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(ICapsule capsule, boolean empty) {
                super.updateItem(capsule, empty);

                if (empty || capsule == null || capsule.getName() == null) {
                    setText(null);
                } else {
                    setText(capsule.getName());
                }
            }
        });
        ObservableList<ICapsule> allContainers = FXCollections.observableList(DOMAIN_FACADE.getDomain().readAllCapsules());
        allContainersListView.setItems(allContainers);
        contentsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(ICapsule capsule, boolean empty) {
                super.updateItem(capsule, empty);

                if (    empty ||
                        capsule == null ||
                        capsule.getName() == null ||
                        !tmpMap.containsKey(capsule) ||
                        DOMAIN_FACADE.getDataFacade().getGFX().getImageMap().get(capsule.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(tmpMap.get(capsule) + "\t" + capsule.getName());
                    ImageView imageView = new ImageView(DOMAIN_FACADE.getDataFacade().getGFX().getImageMap().get(capsule.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void createInvestment(){
        //===Name===
        IVault newInvestment = DOMAIN_FACADE.getFactory().emptyVault().populate(-1,nameTextField.getText());
        //===Content===
        newInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN_FACADE.getDomain().createInvestment(newInvestment);
    }

    private void readInvestment(){
        IVault loadedInvestment = DOMAIN_FACADE.getSelectedInvestment();
        //===Name===
        nameTextField.setText(loadedInvestment.getName());
        //===Content===
        ObservableList<ICapsule> contentsContainers = FXCollections.observableList(new ArrayList<>(loadedInvestment.getItems()));
        contentsListView.setItems(contentsContainers);
        tmpMap = new HashMap<>(loadedInvestment.getAllContainers());
    }

    private void updateInvestment(){
        //===Name===
        loadedInvestment.setName(nameTextField.getText());
        //===Content===
        loadedInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN_FACADE.getDomain().updateInvestment(loadedInvestment);
    }

    private void deleteInvestment(){

    }
}