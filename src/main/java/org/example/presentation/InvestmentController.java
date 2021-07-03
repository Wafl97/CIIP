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

import org.example.logic.Capsule;
import org.example.logic.Interfaces.Investment;

public class InvestmentController extends App implements Initializable {

    @FXML
    private Button saveButton, backButton, addButton, removeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<Capsule> contentsListView;
    @FXML
    private ListView<Capsule> allContainersListView;
    @FXML
    private Spinner<Integer> amountSpinner;

    private Map<Capsule,Long> tmpMap;

    private Investment loadedInvestment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //===Operation==================================================================================================
        Operation operation = getOperation();
        System.out.println(operation);
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
                Capsule capsule = allContainersListView.getItems().get(allContainersListView.getSelectionModel().getSelectedIndex());
                long initialAmount = tmpMap.containsKey(capsule) ? tmpMap.get(capsule) : 0;
                tmpMap.put(capsule, initialAmount + (long) amountSpinner.getValue());
                if (!contentsListView.getItems().contains(capsule)) contentsListView.getItems().add(capsule);
                else contentsListView.refresh();
            }
        });
        removeButton.setOnAction(e -> {
            if (contentsListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0){
                Capsule capsule = contentsListView.getItems().get(contentsListView.getSelectionModel().getSelectedIndex());
                long amount = tmpMap.get(capsule);
                if (amount + amountSpinner.getValue() <= 0) contentsListView.getItems().remove(capsule);
                else tmpMap.put(capsule, amount - amountSpinner.getValue());
                contentsListView.refresh();
            }
        });

        //===ListViews==================================================================================================
        allContainersListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Capsule capsule, boolean empty) {
                super.updateItem(capsule, empty);

                if (empty || capsule == null || capsule.getName() == null) {
                    setText(null);
                } else {
                    setText(capsule.getName());
                }
            }
        });
        ObservableList<Capsule> allContainers = FXCollections.observableList(DOMAIN_FACADE.getDomain().readAllCapsules());
        allContainersListView.setItems(allContainers);
        contentsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Capsule capsule, boolean empty) {
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
        Investment newInvestment = DOMAIN_FACADE.getFactory().emptyVault().populate(-1,nameTextField.getText());
        //===Content===
        newInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN_FACADE.getDomain().createInvestment(newInvestment);
    }

    private void readInvestment(){
        Investment loadedInvestment = DOMAIN_FACADE.getSelectedInvestment();
        //===Name===
        nameTextField.setText(loadedInvestment.getName());
        //===Content===
        ObservableList<Capsule> contentsContainers = FXCollections.observableList(new ArrayList<>(loadedInvestment.getItems()));
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