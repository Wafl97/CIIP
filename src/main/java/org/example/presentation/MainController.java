package org.example.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Investment;

public class MainController extends App implements Initializable {

    @FXML
    private Button createButton, editButton, deleteButton, itemViewButton;
    @FXML
    private ListView<Investment> investmentListView;
    @FXML
    private Label globalTotalInvestedLabel, globalTotalMadeLabel, globalTotalItemsLabel,
            itemName, itemAmount, itemInitPrice, itemCurrPrice, itemDiffPrice, totalContainersLabel,
            totalInvestedLabel, totalSellValueLabel, totalMadeLabel;
    @FXML
    private AnchorPane background;
    @FXML
    private ListView<IItem> itemListView;
    @FXML
    private ImageView itemImage;

    private List<Investment> allInvestments;
    private int investIndex = -1;
    private int itemIndex = -1;


    /**
     *
     * @param url ?
     * @param resourceBundle ?
     */
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
                    DOMAIN.getDomain().deleteInvestment(investmentListView.getItems().get(investmentListView.getSelectionModel().getSelectedIndex()).getId());
                    investmentListView.getItems().remove(investmentListView.getSelectionModel().getSelectedIndex());
                    itemListView.getItems().clear();
                }
            }
        });
        itemViewButton.setOnAction(e -> setRoot("capsuleform",Operation.PASS));

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
            protected void updateItem(IItem container, boolean empty) {
                super.updateItem(container, empty);

                if (empty || container == null || container.getName() == null || DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(container.getName());
                    ImageView imageView = new ImageView(DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
        allInvestments = DOMAIN.getDomain().readAllInvestments();
        float totalInvested = 0;
        int totalItems = 0;
        float totalSell = 0;
        for (Investment investment : allInvestments) {
            for (IItem item : investment.getAllContainers().keySet()){
                totalInvested += item.getInitPrice() * investment.getAllContainers().get(item);
                totalSell += item.getCurrPrice() * investment.getAllContainers().get(item);
                totalItems += investment.getAllContainers().get(item);
            }
        }
        investmentListView.setItems(FXCollections.observableList(allInvestments));

        //===Labels=====================================================================================================
        globalTotalInvestedLabel.setText("Global Total Invested: " + totalInvested);
        globalTotalItemsLabel.setText("Global Total Items: " + totalItems);
        globalTotalMadeLabel.setText("Global Total Made: " + (totalSell - totalInvested));
        totalInvestedLabel.setText("");
        totalContainersLabel.setText("");
        totalSellValueLabel.setText("");
        totalMadeLabel.setText("");
    }

    /**
     *
     * @param mouseEvent
     */
    @FXML
    public void investmentHandler(MouseEvent mouseEvent) {
        investIndex = investmentListView.getSelectionModel().getSelectedIndex();
        if (investIndex != -1) {
            Investment investment = investmentListView.getItems().get(investIndex);
            ObservableList<IItem> containers = FXCollections.observableList(new ArrayList<>(investment.getItems()));
            itemListView.setItems(containers);
            float totalBuy = 0;
            float totalSell = 0;
            int amount = 0;
            for (IItem item : investment.getAllContainers().keySet()){
                totalBuy += item.getInitPrice() * investment.getAllContainers().get(item);
                totalSell += item.getCurrPrice() * investment.getAllContainers().get(item);
                amount += investment.getAllContainers().get(item);
            }
            totalInvestedLabel.setText("Total Invested: " + totalBuy + "€");
            totalContainersLabel.setText("Amount of Containers: " + amount );
            totalSellValueLabel.setText("Total By Selling: " + totalSell);
            totalMadeLabel.setText("Total Made: " + (totalSell - totalBuy));
        }
    }

    /**
     *
     * @param mouseEvent
     */
    @FXML
    public void itemHandler(MouseEvent mouseEvent) {
        itemIndex = itemListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            IItem container = itemListView.getItems().get(itemIndex);
            itemName.setText(container.getName());
            itemImage.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(container.getImage()));
            itemAmount.setText("Amount:\t" + allInvestments.get(investIndex).getAllContainers().get(container));
            itemInitPrice.setText("Buy Price:\t" + container.getInitPrice() + "€");
            itemCurrPrice.setText("Sell Price:\t" + container.getCurrPrice() + "€");
            itemDiffPrice.setText("Diff Price:\t" + container.getDiffPrice() + "€");
        }
    }

    /**
     *
     * @param event the ActionEvent triggering this methode
     */
    public void nice(ActionEvent event) {
        openWeb("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
    }
}
