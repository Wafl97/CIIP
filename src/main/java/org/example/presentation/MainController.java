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

import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.IVault;

public class MainController extends App implements Initializable {

    @FXML
    private Button createButton, editButton, deleteButton, itemViewButton, itemCreateButton, itemUpdateButton;
    @FXML
    private ListView<IVault> investmentListView;
    @FXML
    private Label globalTotalInvestedLabel, globalTotalMadeLabel, globalTotalItemsLabel,
            itemName, itemAmount, itemInitPrice, itemCurrPrice, itemDiffPrice, totalContainersLabel,
            totalInvestedLabel, totalSellValueLabel, totalMadeLabel;
    @FXML
    private AnchorPane background;
    @FXML
    private ListView<Displayable> itemListView;
    @FXML
    private ImageView itemImage;

    private List<IVault> allInvestments;
    private int investIndex = -1;
    private int itemIndex = -1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //===Buttons====================================================================================================
        createButton.setOnAction(e -> goNext(MAIN_PASS, VAULT_CREATE));
        editButton.setOnAction(e -> {
            if (investIndex != -1) {
                DOMAIN_FACADE.setSelectedInvestment(investmentListView.getItems().get(investIndex));
                goNext(MAIN_PASS, VAULT_EDIT);
            }
        });
        deleteButton.setOnAction(e -> {
            if (investmentListView.getSelectionModel().getSelectedIndex() != -1) {
                openWarning("DELETE", "Are you sure?", "Delete the selected element", this::deleteInvestment,false);
            }
        });
        itemViewButton.setOnAction(e -> goNext(MAIN_PASS, ITEM_PASS));
        itemCreateButton.setOnAction(e -> goNext(MAIN_PASS, ITEM_CREATE));
        itemUpdateButton.setOnAction(e -> goNext(MAIN_PASS, ITEM_EDIT));

        //===ListViews==================================================================================================
        investmentListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(IVault investment, boolean empty) {
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
            protected void updateItem(Displayable capsule, boolean empty) {
                super.updateItem(capsule, empty);

                if (empty || capsule == null || capsule.getName() == null || DOMAIN_FACADE.getDataFacade().getGFX().getImageMap().get(capsule.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(capsule.getName());
                    ImageView imageView = new ImageView(DOMAIN_FACADE.getDataFacade().getGFX().getImageMap().get(capsule.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(25);
                    setGraphic(imageView);
                }
            }
        });
        allInvestments = DOMAIN_FACADE.getDomain().readAllVaults();
        float totalInvested = 0;
        int totalItems = 0;
        float totalSell = 0;
        for (IVault investment : allInvestments) {
            for (Object obj : investment.getAllItems().keySet()){
                Displayable item = (Displayable) obj;
                long l = (long) investment.getAllItems().get(item);
                totalInvested += item.getInitPrice() * l;
                totalSell += item.getCurrPrice() * l;
                totalItems += l;
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

    private void deleteInvestment() {
        DOMAIN_FACADE.getDomain().deleteVault(investmentListView.getItems().get(investmentListView.getSelectionModel().getSelectedIndex()).getId());
        investmentListView.getItems().remove(investmentListView.getSelectionModel().getSelectedIndex());
        itemListView.getItems().clear();
    }

    /**
     *
     * @param mouseEvent
     */
    @FXML
    public void investmentHandler(MouseEvent mouseEvent) {
        investIndex = investmentListView.getSelectionModel().getSelectedIndex();
        if (investIndex != -1) {
            IVault investment = investmentListView.getItems().get(investIndex);
            ObservableList<Displayable> capsules = FXCollections.observableList(new ArrayList<>(investment.getItems()));
            itemListView.setItems(capsules);
            float totalBuy = 0;
            float totalSell = 0;
            int amount = 0;
            for (Object obj : investment.getAllItems().keySet()){
                Displayable item = (Displayable) obj;
                long l = (long) investment.getAllItems().get(item);
                totalBuy += item.getInitPrice() * l;
                totalSell += item.getCurrPrice() * l;
                amount += l;
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
            Displayable capsule = itemListView.getItems().get(itemIndex);
            itemName.setText(capsule.getName());
            itemImage.setImage(DOMAIN_FACADE.getDataFacade().getGFX().getImageMap().get(capsule.getImage()));
            itemAmount.setText("Amount:\t" + allInvestments.get(investIndex).getAllItems().get(capsule));
            itemInitPrice.setText("Buy Price:\t" + capsule.getInitPrice() + "€");
            itemCurrPrice.setText("Sell Price:\t" + capsule.getCurrPrice() + "€");
            itemDiffPrice.setText("Diff Price:\t" + capsule.getDiffPrice() + "€");
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
