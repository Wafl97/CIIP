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
import javafx.util.Pair;

import org.example.logic.DomainFacade;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * Link to logic layer
     */
    static final DomainFacade DOMAIN_FACADE = DomainFacade.getInstance();

    private static Scene scene;

    private static Operation operation;

    private static final Stack<Pair<String,Operation>> fxmlStack = new Stack<>();

    static final String TITLE = "CIP";
    static final String INVESTMENTFORM = "investmentform";
    static final String MAIN = "main";
    static final String CAPSULEFORM = "capsuleform";

    static final Operation PASS = Operation.PASS;
    static final Operation EDIT = Operation.EDIT;
    static final Operation CREATE = Operation.CREATE;

    final Pair<String,Operation> MAIN_PASS = new Pair<>(MAIN,PASS);

    final Pair<String,Operation> CAPSULE_PASS = new Pair<>(CAPSULEFORM,PASS);
    final Pair<String,Operation> CAPSULE_EDIT = new Pair<>(CAPSULEFORM,EDIT);
    final Pair<String,Operation> CAPSULE_CREATE = new Pair<>(CAPSULEFORM,CREATE);

    final Pair<String,Operation> INVEST_PASS = new Pair<>(INVESTMENTFORM,PASS);
    final Pair<String,Operation> INVEST_EDIT = new Pair<>(INVESTMENTFORM,EDIT);
    final Pair<String,Operation> INVEST_CREATE = new Pair<>(INVESTMENTFORM,CREATE);

    @Override
    public void start(Stage stage) throws IOException {
        //Initial set FXML and Operation
        //fxmlStack.push(new Pair<>(MAIN,PASS));

        scene = new Scene(loadFXML(MAIN), 1080, 720);
        stage.getIcons().add(DOMAIN_FACADE.getDataFacade().getGFX().getLogo());
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setOperation(Operation op){
        operation = op;
    }

    Operation getOperation(){
        return operation;
    }

    void goBack(){
        if (!fxmlStack.empty()) {
            Pair<String, Operation> tmp = fxmlStack.pop();
            setOperation(tmp.getValue());
            setRoot(tmp.getKey());
            return;
        }
        setOperation(PASS);
        setRoot(MAIN);
    }

    void goNext(Pair<String,Operation> current, Pair<String,Operation> next){
        fxmlStack.push(current);
        setOperation(next.getValue());
        setRoot(next.getKey());
    }

    void openWarning(String title, String shortMes, String longMes, CallBack callBack, boolean goBack){
        boolean answer = openWarningWindow(title, shortMes,longMes);
        if (answer) callBack.exec();
        if (goBack) goBack();
    }

    boolean openWarningWindow(String title,String shortMes, String longMes){
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
        stage.getIcons().add(DOMAIN_FACADE.getDataFacade().getGFX().getLogo());
        stage.setScene(new Scene(pane,300,100));
        stage.showAndWait();
        return answer.get();
    }

    /**
     * This methode opens the system default browser on the given url
     * @param url for the browser
     */
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

    interface CallBack{
        void exec();
    }
}