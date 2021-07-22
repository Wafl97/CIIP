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

    private List<IVault> allVaults;
    private int investIndex = -1;
    private int itemIndex = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //===Buttons====================================================================================================
        createButton.setOnAction(e -> goNext(MAIN_PASS, VAULT_CREATE));
        editButton.setOnAction(e -> {
            if (investIndex != -1) {
                DOMAIN.setSelectedVault(investmentListView.getItems().get(investIndex));
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
            protected void updateItem(Displayable item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null || DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()) == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getName());
                    ImageView imageView = new ImageView(DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(IMAGE_ICON_SIZE);
                    setGraphic(imageView);
                }
            }
        });
        allVaults = DOMAIN.readAllVaults();
        Info info = new Info();
        for (IVault vault : allVaults) {
            calcInfo(vault, info);
        }
        investmentListView.setItems(FXCollections.observableList(allVaults));

        //===Labels=====================================================================================================
        globalTotalInvestedLabel.setText("Global Total Invested: " + info.getBuy());
        globalTotalItemsLabel.setText("Global Total Items: " + info.getAmount());
        globalTotalMadeLabel.setText("Global Total Made: " + (info.getSell() - info.getBuy()));
        totalInvestedLabel.setText("");
        totalContainersLabel.setText("");
        totalSellValueLabel.setText("");
        totalMadeLabel.setText("");
    }

    private void deleteInvestment() {
        DOMAIN.deleteVault(investmentListView.getItems().get(investmentListView.getSelectionModel().getSelectedIndex()).getId());
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
            IVault vault = investmentListView.getItems().get(investIndex);
            ObservableList<Displayable> item = FXCollections.observableList(new ArrayList<>(vault.getItems()));
            itemListView.setItems(item);
            Info info = new Info();
            calcInfo(vault,info);
            totalInvestedLabel.setText("Total Invested: " + info.getBuy() + "€");
            totalContainersLabel.setText("Amount of Containers: " + info.getAmount() );
            totalSellValueLabel.setText("Total By Selling: " + info.getSell());
            totalMadeLabel.setText("Total Made: " + (info.getSell() - info.getBuy()));
        }
    }

    /**
     * Calculates the different totals in a Vault.
     * This methode updates the int and float references.
     * @param vault the Vault being calculated on
     */
    private void calcInfo(IVault vault, Info info){
        for (Object obj : vault.getAllItems().keySet()){
            Displayable item = (Displayable) obj;
            long l = vault.getAllItems().get(item);
            info.add2Amount(l);
            info.add2Buy((item.getInitPrice() * l));
            info.add2Sell((item.getCurrPrice() * l));
        }
    }

    @FXML
    public void itemHandler(MouseEvent mouseEvent) {
        itemIndex = itemListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            Displayable capsule = itemListView.getItems().get(itemIndex);
            itemName.setText(capsule.getName());
            itemImage.setImage(DOMAIN.getDataFacade().getGFX().getImageMap().get(capsule.getImage()));
            itemImage.setPreserveRatio(true);
            itemImage.setFitHeight(IMAGE_SIZE);
            itemAmount.setText("Amount:\t" + allVaults.get(investIndex).getAllItems().get(capsule));
            itemInitPrice.setText("Buy Price:\t" + capsule.getInitPrice() + "€");
            itemCurrPrice.setText("Sell Price:\t" + capsule.getCurrPrice() + "€");
            itemDiffPrice.setText("Diff Price:\t" + capsule.getDiffPrice() + "€");
        }
    }

    public void nice(ActionEvent event) {
        openWeb("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
    }


    //This might be a bit overkill
    private static final class Info{

        private long amount;
        private double buy;
        private double sell;

        private Info(){

        }
        private void add2Amount(long amount){
            this.amount += amount;
        }
        private long getAmount(){
            return amount;
        }
        private void add2Buy(double buy){
            this.buy += buy;
        }
        private double getBuy(){
            return buy;
        }
        private void add2Sell(double sell){
            this.sell += sell;
        }
        private double getSell(){
            return sell;
        }
    }
}
