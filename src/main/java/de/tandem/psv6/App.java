package de.tandem.psv6;

import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.GUIOwner;
import de.tandem.psv6.gui.LogIn;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
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
        stage.setTitle("PasswordSaverV6");
        stage.setWidth(1280);
        stage.setHeight(720);

        root = new BorderPane();
        root.getStylesheets().add(style);

        flowPane = new FlowPane();
        flowPane.setId("gridPane");
        flowPane.getStylesheets().add(style);
        flowPane.hgapProperty().bind(hScale.multiply(20));
        flowPane.vgapProperty().bind(vScale.multiply(20));
        flowPane.setPadding(new Insets(20));

        var scroll = new ScrollPane();
        scroll.setId("gridPane");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scroll.setContent(flowPane);
        scroll.viewportBoundsProperty().addListener((ov, oldBounds, bounds) -> {
            flowPane.setPrefWidth(bounds.getWidth());
            flowPane.setPrefHeight(bounds.getHeight());
        });
        root.setCenter(scroll);

        var scene = new Scene(root);
        scene.getStylesheets().add(style);
        root.setPrefWidth(1280);
        root.setPrefHeight(720);

        vScale.bind(scene.heightProperty().divide(1280));
        hScale.bind(scene.widthProperty().divide(720));

        stage.setScene(scene);
        stage.getIcons().add(new Image("img/PasswordSaverV6.png"));
        new LogIn(this);
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

    public void removeEntry(Entry entry) {
        flowPane.getChildren().remove(Database.getInstance().getAllEntries().indexOf(entry) + 1);
    }

}
