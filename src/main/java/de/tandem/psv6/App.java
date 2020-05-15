package de.tandem.psv6;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    public static final long VERSION = 14L;
    private Stage primaryStage;
    private BorderPane root;
    private GridPane gridPane;
    private DoubleProperty hScale;
    private DoubleProperty vScale;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Password Saver");
        this.primaryStage.setWidth(1280);
        this.primaryStage.setHeight(720);

        root = new BorderPane();

        gridPane = new GridPane();
        gridPane.setId("gridPane");
        gridPane.setAlignment(Pos.CENTER);
        root.setCenter(gridPane);

        Scene scene = new Scene(root);

        hScale = new SimpleDoubleProperty();
        hScale.bind(scene.widthProperty().divide(720));
        vScale = new SimpleDoubleProperty();
        vScale.bind(scene.heightProperty().divide(1280));

        ColumnConstraints cs = new ColumnConstraints();
        cs.maxWidthProperty().bind(hScale.multiply(150));
        cs.minWidthProperty().bind(hScale.multiply(80));
        gridPane.getColumnConstraints().addAll(cs, cs, cs, cs);

        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Save Settings
        super.stop();
    }

}
