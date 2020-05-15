package de.tandem.psv6;

import de.tandem.psv6.gui.LogIn;
import de.tandem.psv6.gui.MenuBar;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class App extends Application {
    public static final long VERSION = 14L;
    private String style;
    private Stage stage;
    private BorderPane root;
    private GridPane gridPane;
    private DoubleProperty hScale;
    private DoubleProperty vScale;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        style = "dark-styles.css"; //TODO Config
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

        Scene scene = new Scene(root);

        hScale = new SimpleDoubleProperty();
        hScale.bind(scene.widthProperty().divide(720));
        vScale = new SimpleDoubleProperty();
        vScale.bind(scene.heightProperty().divide(1280));

        ColumnConstraints cs = new ColumnConstraints();
        cs.maxWidthProperty().bind(hScale.multiply(150));
        cs.minWidthProperty().bind(hScale.multiply(80));
        gridPane.getColumnConstraints().addAll(cs, cs, cs, cs);

        stage.setScene(scene);
        new LogIn(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public double getHScale() {
        return hScale.doubleValue();
    }

    public double getVScale() {
        return vScale.doubleValue();
    }
}
