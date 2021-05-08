package org.example.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.data.GFX;
import org.example.logic.Capsule;
import org.example.logic.Domain;
import org.example.logic.Logic;
import org.example.logic.SouvenirCase;

public class PrimaryController implements Initializable {

    @FXML
    private Button b_Create,b_Read,b_Update,b_Delete;
    @FXML
    private HBox xd;

    private static final Logic DOMAIN = Domain.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        b_Create.setOnAction(e -> DOMAIN.createContainer(new SouvenirCase()));
        b_Read.setOnAction(e -> DOMAIN.readContainer(new Capsule()));
        b_Update.setOnAction(e -> DOMAIN.updateContainer(new SouvenirCase()));
        b_Delete.setOnAction(e -> DOMAIN.deleteContainer(new SouvenirCase()));
        List<ImageView> imageViewList = new ArrayList<>();

        Set<String> ks = GFX.getInstance().getImageMap().keySet();
        for (String s : ks){
            ImageView i = new ImageView();
            i.setImage(GFX.getInstance().getImageMap().get(s));
            i.setPreserveRatio(true);
            i.setFitHeight(150);
            imageViewList.add(i);
        }
        xd.getChildren().addAll(imageViewList);
    }
}
