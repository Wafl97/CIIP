package org.example.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.logic.DomainFacade;
import org.example.logic.Interfaces.IDomainFacade;
import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Factory;
import org.example.logic.Interfaces.Investment;
import org.example.logic.StructureCreator;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Link to logic layer
     */
    static final DomainFacade DOMAIN = DomainFacade.getInstance();

    /**
     * Date factory
     */
    static final Factory CREATOR = StructureCreator.getInstance();

    /**
     * App instance
     */
    private static App instance;

    static Operation operation;

    static Investment selectedInvestment;

    static IItem selectedContainer;

    static final String INVESTMENTFORM = "investmentform";
    static final String MAIN = "main";

    public static App getInstance(){
        return instance == null ? instance = new App() : instance;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 1080, 720);
        stage.getIcons().add(DOMAIN.getDataFacade().getGFX().getLogo());
        stage.setTitle("CIP");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml, Operation op) {
        operation = op;
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean openWarning(String title,String shortMes, String longMes){
        Stage stage = new Stage();
        AtomicBoolean answer = new AtomicBoolean(false);
        AnchorPane pane = new AnchorPane();
        pane.setId("background");
        String css = App.class.getResource("/org/example/css/cip.css").toExternalForm();
        pane.getStylesheets().add(css);
        HBox hBox = new HBox();
        Label shortM = new Label(shortMes);
        Label longM = new Label(longMes);
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer.set(true);
            stage.close();
        });
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer.set(false);
            stage.close();
        });
        hBox.setLayoutX(175); hBox.setLayoutY(50);
        shortM.setLayoutX(25); shortM.setLayoutY(50);
        longM.setLayoutX(25); longM.setLayoutY(25);
        hBox.getChildren().addAll(yesButton,noButton);
        pane.getChildren().addAll(longM,shortM,hBox);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(DOMAIN.getDataFacade().getGFX().getLogo());
        stage.setScene(new Scene(pane,300,100));
        stage.showAndWait();
        return answer.get();
    }

    void openWeb(String url){
        getHostServices().showDocument(url);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    enum Operation{
        CREATE,
        EDIT,
        PASS;
    }
}