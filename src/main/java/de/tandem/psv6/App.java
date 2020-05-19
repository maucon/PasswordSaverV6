package de.tandem.psv6;

import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.GUIOwner;
import de.tandem.psv6.gui.LogIn;
import de.tandem.psv6.gui.MenuBar;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class App extends Application implements GUIOwner {
    public static final boolean DEBUG = false;
    public static final long VERSION = 14L;
    private Stage stage;
    private String style;
    private BorderPane root;
    private GridPane gridPane;

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

        new MenuBar(this);

        gridPane = new GridPane();
        gridPane.setId("gridPane");
        gridPane.getStylesheets().add(style);
        gridPane.setAlignment(Pos.CENTER);
        root.setCenter(gridPane);

        var scene = new Scene(root);
        root.setPrefWidth(1280);
        root.setPrefHeight(720);

        vScale.bind(scene.heightProperty().divide(1280));
        hScale.bind(scene.widthProperty().divide(720));

        var cs = new ColumnConstraints();
        cs.maxWidthProperty().bind(hScale.multiply(150));
        cs.minWidthProperty().bind(hScale.multiply(80));
        gridPane.getColumnConstraints().addAll(cs, cs, cs, cs);

        stage.setScene(scene);
        if (DEBUG) stage.show();
        else new LogIn(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public void updateStyle() {
        style = Settings.darkMode ? "dark-styles.css" : "light-styles.css";
        root.getStylesheets().clear();
        root.getStylesheets().add(style);
        gridPane.getStylesheets().clear();
        gridPane.getStylesheets().add(style);
    }
}
