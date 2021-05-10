package org.example.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.logic.Interfaces.Container;
import org.example.logic.Interfaces.Investment;

public class MainController extends App implements Initializable {

    @FXML
    private Button createButton, editButton, deleteButton;
    @FXML
    private ListView<Investment> investmentListView;
    @FXML
    private Label balanceLabel, itemName, itemAmount, itemPrice, totalContainersLabel, investmentTotalLabel;
    @FXML
    private AnchorPane background;
    @FXML
    private ListView<Container> itemListView;
    @FXML
    private ImageView itemImage;

    //TODO temp
    @FXML
    private Button tmpButton;

    private float balance = 0;
    private List<Investment> allInvestments;
    private int investIndex = -1;
    private int itemIndex = -1;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //===Buttons====================================================================================================
        createButton.setOnAction(e -> setRoot(INVESTMENTFORM,Operation.CREATE));
        editButton.setOnAction(e -> {
            if (investIndex != -1) {
                selectedInvestment = investmentListView.getItems().get(investIndex);
                setRoot(INVESTMENTFORM, Operation.EDIT);
            }
        });
        deleteButton.setOnAction(e -> {
            if (investmentListView.getSelectionModel().getSelectedIndex() != -1) {
                if (openWarning("DELETE", "Are you sure?", "Delete the selected element")) {
                    DOMAIN.deleteInvestment(investmentListView.getItems().get(investmentListView.getSelectionModel().getSelectedIndex()).getId());
                    investmentListView.getItems().remove(investmentListView.getSelectionModel().getSelectedIndex());
                    itemListView.getItems().clear();
                }
            }
        });
        tmpButton.setOnAction(e -> setRoot("containerform",Operation.PASS));

        //===ListViews==================================================================================================
        investmentListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Investment investment, boolean empty) {
                super.updateItem(investment, empty);

                if (empty || investment == null || investment.getName() == null) {
                    setText(null);
                } else {
                    setText(investment.getName());
                }
            }
        });
        itemListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Container container, boolean empty) {
                super.updateItem(container, empty);

                if (empty || container == null || container.getName() == null || DOMAIN.getGFX().getImageMap().get(container.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(container.getName());
                    ImageView imageView = new ImageView(DOMAIN.getGFX().getImageMap().get(container.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
        allInvestments = DOMAIN.readAllInvestments();
        for (Investment investment : allInvestments) {
            for (Container container : investment.getAllContainers().keySet()){
                balance += container.getPrice() * investment.getAllContainers().get(container);
            }
        }
        investmentListView.setItems(FXCollections.observableList(new ArrayList<>(allInvestments)));

        //===Labels=====================================================================================================
        balanceLabel.setText("Total: " + balance);
        investmentTotalLabel.setText("");
        totalContainersLabel.setText("");
    }

    @FXML
    public void investmentHandler(MouseEvent mouseEvent) {
        investIndex = investmentListView.getSelectionModel().getSelectedIndex();
        if (investIndex != -1) {
            Investment investment = investmentListView.getItems().get(investIndex);
            ObservableList<Container> containers = FXCollections.observableList(new ArrayList<>(investment.getContainers()));
            itemListView.setItems(containers);
            float total = 0;
            int amount = 0;
            for (Container container : investment.getAllContainers().keySet()){
                total += container.getPrice() * investment.getAllContainers().get(container);
                amount += investment.getAllContainers().get(container);
            }
            investmentTotalLabel.setText("Investment Total: " + total);
            totalContainersLabel.setText("Amount of Containers: " + amount + "€");
        }
    }

    @FXML
    public void itemHandler(MouseEvent mouseEvent) {
        itemIndex = itemListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            Container container = itemListView.getItems().get(itemIndex);
            itemName.setText(container.getName());
            itemImage.setImage(DOMAIN.getGFX().getImageMap().get(container.getImage()));
            itemAmount.setText("Amount:\t" + allInvestments.get(investIndex).getAllContainers().get(container));
            itemPrice.setText("Price:\t" + container.getPrice() + "€");
        }
    }
}
