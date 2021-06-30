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
import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Investment;

public class InvestmentController extends App implements Initializable {

    @FXML
    private Button saveButton, backButton, addButton, removeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<IItem> contentsListView;
    @FXML
    private ListView<IItem> allContainersListView;
    @FXML
    private Spinner<Integer> amountSpinner;

    private Map<IItem,Long> tmpMap;

    private Investment loadedInvestment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //===Operation==================================================================================================
        Operation operation = getOperation();
        System.out.println(operation);
        if (operation == EDIT) readInvestment();
        else if (operation == CREATE){
            tmpMap = new HashMap<>();
            setSelectedInvestment(CREATOR.emptyVault());
        }

        //===Buttons====================================================================================================
        backButton.setOnAction(e -> App.getInstance().goBack());
        saveButton.setOnAction(e -> {
            if (operation == CREATE) createInvestment();
            else if (operation == EDIT) updateInvestment();
        });
        addButton.setOnAction(e -> {
            if (allContainersListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0) {
                IItem container = allContainersListView.getItems().get(allContainersListView.getSelectionModel().getSelectedIndex());
                long initialAmount = tmpMap.containsKey(container) ? tmpMap.get(container) : 0;
                tmpMap.put(container, initialAmount + (long) amountSpinner.getValue());
                if (!contentsListView.getItems().contains(container)) contentsListView.getItems().add(container);
                else contentsListView.refresh();
            }
        });
        removeButton.setOnAction(e -> {
            if (contentsListView.getSelectionModel().getSelectedIndex() != -1 && amountSpinner.getValue() > 0){
                IItem container = contentsListView.getItems().get(contentsListView.getSelectionModel().getSelectedIndex());
                long amount = tmpMap.get(container);
                if (amount + amountSpinner.getValue() <= 0) contentsListView.getItems().remove(container);
                else tmpMap.put(container, amount - amountSpinner.getValue());
                contentsListView.refresh();
            }
        });

        //===ListViews==================================================================================================
        allContainersListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(IItem container, boolean empty) {
                super.updateItem(container, empty);

                if (empty || container == null || container.getName() == null) {
                    setText(null);
                } else {
                    setText(container.getName());
                }
            }
        });
        ObservableList<IItem> allContainers = FXCollections.observableList(DOMAIN.getDomain().readAllContainers());
        allContainersListView.setItems(allContainers);
        contentsListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(IItem container, boolean empty) {
                super.updateItem(container, empty);

                if (    empty ||
                        container == null ||
                        container.getName() == null ||
                        !tmpMap.containsKey(container) ||
                        DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(tmpMap.get(container) + "\t" + container.getName());
                    ImageView imageView = new ImageView(DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()));
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
        newInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN.getDomain().createInvestment(newInvestment);
    }

    private void readInvestment(){
        Investment loadedInvestment = getSelectedInvestment();
        //===Name===
        nameTextField.setText(loadedInvestment.getName());
        //===Content===
        ObservableList<IItem> contentsContainers = FXCollections.observableList(new ArrayList<>(loadedInvestment.getItems()));
        contentsListView.setItems(contentsContainers);
        tmpMap = new HashMap<>(loadedInvestment.getAllContainers());
    }

    private void updateInvestment(){
        //===Name===
        loadedInvestment.setName(nameTextField.getText());
        //===Content===
        loadedInvestment.setAllItems(tmpMap);
        //===Domain===
        DOMAIN.getDomain().updateInvestment(loadedInvestment.getId(), loadedInvestment);
    }

    private void deleteInvestment(){

    }
}