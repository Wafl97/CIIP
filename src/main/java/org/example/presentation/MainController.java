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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.example.logic.dto.interfaces.*;
import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.util.ConsoleColors;

public class MainController extends App implements Initializable {

    @FXML
    private Button createButton, editButton, deleteButton, itemViewButton, itemCreateButton, itemUpdateButton;
    @FXML
    private ListView<IVault> investmentListView;
    @FXML
    private Label itemName, itemAmount, itemInitPrice, itemCurrPrice, itemDiffPrice,
            globalTotalInvestedLabelValue, globalTotalItemsLabelValue, globalTotalMadeLabelValue,
            totalContainersLabelValue, totalInvestedLabelValue, totalSellValueLabelValue, totalMadeLabelValue,
            itemAmountValue, itemInitPriceValue, itemCurrPriceValue, itemDiffPriceValue;
    @FXML
    private ListView<Displayable> itemListView;
    @FXML
    private ImageView itemImage;

    private List<IVault> allVaults;
    private int investIndex = -1;
    private int itemIndex = -1;

    private static final char CURRENCY = '€';

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
                openWarning("DELETE", "Are you sure?", "Delete the selected element", this::deleteInvestment, false);
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
                    ImageView imageView = new ImageView(new Image(DOMAIN.getDataFacade().getGFX().getImageMap().get(item.getImage()).toURI().toString()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(IMAGE_ICON_SIZE);
                    setGraphic(imageView);
                }
            }
        });
        allVaults = new ArrayList<>();
        for (Object item : DOMAIN.getVaultDomain().readAll()){
            IVault displayable = (IVault) item;
            allVaults.add(displayable);
        }
        investmentListView.setItems(FXCollections.observableList(allVaults));

        //===Labels=====================================================================================================
        updateLabels();
    }

    private void deleteInvestment() {
        int vault_id = investmentListView.getSelectionModel().getSelectedIndex();
        IVault vault = investmentListView.getItems().get(vault_id);
        DOMAIN.getVaultDomain().delete(vault.getId());
        investmentListView.getItems().remove(vault);
        itemListView.getItems().clear();
        investmentListView.refresh();
        if (investmentListView.getItems().isEmpty()) investmentListView.getItems().clear();
    }

    @FXML
    private void investmentHandler() {
        investIndex = investmentListView.getSelectionModel().getSelectedIndex();
        if (investIndex != -1 && investIndex < investmentListView.getItems().size()) {
            IVault vault = investmentListView.getItems().get(investIndex);
            ObservableList<Displayable> item = FXCollections.observableList(new ArrayList<>(vault.getItems()));
            itemListView.setItems(item);
            Info info = new Info();
            calcInfo(vault,info);
            totalContainersLabelValue.setText(String.valueOf(info.getAmount()));
            totalInvestedLabelValue.setText(String.format("%.2f",info.getBuy()) + CURRENCY);
            totalSellValueLabelValue.setText(String.format("%.2f",info.getSell()) + CURRENCY);
            totalMadeLabelValue.setText(String.format("%.2f",info.getSell() - info.getBuy()) + CURRENCY);
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

    private void updateLabels(){
        Info info = new Info();
        for (IVault vault : allVaults) {
            calcInfo(vault, info);
        }
        globalTotalItemsLabelValue.setText(String.valueOf(info.getAmount()));
        globalTotalInvestedLabelValue.setText(String.format("%.2f",info.getBuy())  + CURRENCY);
        globalTotalMadeLabelValue.setText(String.format("%.2f",(info.getSell() - info.getBuy())) + CURRENCY);
        totalContainersLabelValue.setText("--");
        totalInvestedLabelValue.setText("--.--");
        totalSellValueLabelValue.setText("--.--");
        totalMadeLabelValue.setText("--.--");
    }

    private void updateItem(Transferable transferable){
        if (transferable instanceof ISkin) {
            DOMAIN.getSkinDomain().update(transferable);
        }
        else if (transferable instanceof ICapsule) {
            DOMAIN.getCapsuleDomain().update(transferable);
        }
        else if (transferable instanceof ISouvenirCase) {
            DOMAIN.getSouvenirCaseDomain().update(transferable);
        }
        else if (transferable instanceof ISticker){
            DOMAIN.getStickerDomain().update(transferable);
        }
        else if (transferable instanceof IPatch){
            DOMAIN.getPatchDomain().update(transferable);
        }
        else if (transferable instanceof ICase){
            DOMAIN.getCaseDomain().update(transferable);
        }
        else if (transferable instanceof ITicket){
            DOMAIN.getTicketDomain().update(transferable);
        }
        else if (transferable instanceof IKey){
            DOMAIN.getKeyDomain().update(transferable);
        }
        else if (transferable instanceof IMusicKit){
            DOMAIN.getMusicKitDomain().update(transferable);
        }
        else if (transferable instanceof IPin){
            DOMAIN.getPinDomain().update(transferable);
        }
        else if (transferable instanceof IPlayerModel){
            DOMAIN.getPlayerModelDomain().update(transferable);
        }
        else if (transferable instanceof IGraffiti){
            DOMAIN.getGraffitiDomain().update(transferable);
        }
    }

    @FXML
    private void itemHandler() {
        itemIndex = itemListView.getSelectionModel().getSelectedIndex();
        if (itemIndex != -1) {
            Displayable capsule = itemListView.getItems().get(itemIndex);
            itemName.setText(capsule.getName());
            itemImage.setImage(new Image(DOMAIN.getDataFacade().getGFX().getImageMap().get(capsule.getImage()).toURI().toString()));
            itemImage.setPreserveRatio(true);
            itemImage.setFitHeight(IMAGE_SIZE);
            itemAmount.setText("Amount: ");
            itemAmountValue.setText(String.valueOf(allVaults.get(investIndex).getAllItems().get(capsule)));
            itemInitPrice.setText("Buy Price: ");
            itemInitPriceValue.setText(String.format("%.2f",capsule.getInitPrice()) + CURRENCY);
            itemCurrPrice.setText("Sell Price: ");
            itemCurrPriceValue.setText(String.format("%.2f",capsule.getCurrPrice()) + CURRENCY);
            itemDiffPrice.setText("Diff Price: ");
            itemDiffPriceValue.setText(String.format("%.2f",capsule.getDiffPrice()) + CURRENCY);
        }
    }

    @FXML
    private void toggleCSS(){
        CSS = toggleFlag ? CIIP_CSS : DFLT_CSS;
        updateCSS();
        toggleFlag = !toggleFlag;
    }

    @FXML
    private void updatePrices(){
        System.out.println("\nUpdating prices for all items\n");
        System.out.println("This might take some time...\n");
        allVaults.forEach(vault -> vault.getAllItems().keySet().forEach(displayable -> {
            displayable.updateCurrPrice();
            updateItem((Transferable) displayable);
        }));
        updateLabels();
        investmentHandler();
        itemHandler();
        System.out.println(ConsoleColors.GREEN + "\nAll prices are up to date\n" + ConsoleColors.RESET);
    }

    @FXML
    private void nice() {
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
