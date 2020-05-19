package de.tandem.psv6;

import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.GUIOwner;
import de.tandem.psv6.gui.LogIn;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class App extends Application implements GUIOwner {
    public static final long VERSION = 14L;
    private Stage stage;
    private String style;
    private BorderPane root;
    private FlowPane flowPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        style = "dark-styles.css";
        stage = primaryStage;
        stage.setTitle("Password Saver");
        stage.setWidth(1280);
        stage.setHeight(720);

        root = new BorderPane();
        root.getStylesheets().add(style);

        flowPane = new FlowPane();
        flowPane.setId("gridPane");
        flowPane.getStylesheets().add(style);
        flowPane.setAlignment(Pos.CENTER);
        root.setCenter(flowPane);

        var scene = new Scene(root);
        root.setPrefWidth(1280);
        root.setPrefHeight(720);

        vScale.bind(scene.heightProperty().divide(1280));
        hScale.bind(scene.widthProperty().divide(720));

        stage.setScene(scene);
        new LogIn(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public void updateStyle() {
        style = Settings.darkMode ? "dark-styles.css" : "light-styles.css";
        root.getStylesheets().clear();
        root.getStylesheets().add(style);
        flowPane.getStylesheets().clear();
        flowPane.getStylesheets().add(style);
    }

    public void addNode(Node node) {
        flowPane.getChildren().add(node);
    }
}
