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
import org.example.logic.Interfaces.Container;
import org.example.logic.Interfaces.Investment;

public class InvestmentController extends App implements Initializable {

    @FXML
    private Button saveButton, backButton, addButton, removeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<Container> contentsListView;
    @FXML
    private ListView<Container> allContainersListView;
    @FXML
    private Spinner<Integer> amountSpinner;

    private Map<Container,Long> tmpMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //===Operation==================================================================================================
        System.out.println(operation);
        if (operation == Operation.EDIT) readInvestment();
        else if (operation == Operation.CREATE){
            tmpMap = new HashMap<>();
            selectedInvestment = CREATOR.emptyVault();
        }

        //===Buttons====================================================================================================
        backButton.setOnAction(e -> setRoot(MAIN,Operation.PASS));
        saveButton.setOnAction(e -> {
            if (operation == Operation.CREATE) createInvestment();
            else if (operation == Operation.EDIT) updateInvestment();
        });
        addButton.setOnAction(e -> {
            if (allContainersListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0) {
                Container container = allContainersListView.getItems().get(allContainersListView.getSelectionModel().getSelectedIndex());
                long initialAmount = tmpMap.containsKey(container) ? tmpMap.get(container) : 0;
                tmpMap.put(container, initialAmount + (long) amountSpinner.getValue());
                if (!contentsListView.getItems().contains(container)) contentsListView.getItems().add(container);
                else contentsListView.refresh();
            }
        });
        removeButton.setOnAction(e -> {
            if (contentsListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0){
                Container container = contentsListView.getItems().get(contentsListView.getSelectionModel().getSelectedIndex());
                long amount = tmpMap.get(container);
                if (amount + amountSpinner.getValue() <= 0) contentsListView.getItems().remove(container);
                else tmpMap.put(container, amount - amountSpinner.getValue());
                contentsListView.refresh();
            }
        });

        //===ListViews==================================================================================================
        allContainersListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Container container, boolean empty) {
                super.updateItem(container, empty);

                if (empty || container == null || container.getName() == null) {
                    setText(null);
                } else {
                    setText(container.getName());
                }
            }
        });
        ObservableList<Container> allContainers = FXCollections.observableList(DOMAIN.readAllContainers());
        allContainersListView.setItems(allContainers);
        contentsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Container container, boolean empty) {
                super.updateItem(container, empty);

                if (    empty ||
                        container == null ||
                        container.getName() == null ||
                        !tmpMap.containsKey(container) ||
                        DOMAIN.getGFX().getImageMap().get(container.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(tmpMap.get(container) + "\t" + container.getName());
                    ImageView imageView = new ImageView(DOMAIN.getGFX().getImageMap().get(container.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void createInvestment(){
        //===Name===
        Investment newInvestment = CREATOR.emptyVault().populate(-1,nameTextField.getText());
        //===Content===
        newInvestment.setAllContainers(tmpMap);
        //===Domain===
        DOMAIN.createInvestment(newInvestment);
    }

    private void readInvestment(){
        //===Name===
        nameTextField.setText(selectedInvestment.getName());
        //===Content===
        ObservableList<Container> contentsContainers = FXCollections.observableList(new ArrayList<>(selectedInvestment.getContainers()));
        contentsListView.setItems(contentsContainers);
        tmpMap = new HashMap<>(selectedInvestment.getAllContainers());
    }

    private void updateInvestment(){
        //===Name===
        selectedInvestment.setName(nameTextField.getText());
        //===Content===
        selectedInvestment.setAllContainers(tmpMap);
        //===Domain===
        DOMAIN.updateInvestment(selectedInvestment.getId(), selectedInvestment);
    }

    private void deleteInvestment(){

    }
}