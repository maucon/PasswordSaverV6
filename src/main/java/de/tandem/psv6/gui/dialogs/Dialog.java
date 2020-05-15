package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public abstract class Dialog {
    Stage stage;
    GridPane grid;
    App app;
    EventHandler<WindowEvent> closeEventHandler;

    public Dialog(String title, double width, double height, App app) {
        this.app = app;

        grid = new GridPane();
        grid.setId("gridPane");
        grid.getStylesheets().add(app.getStyle());
        grid.setAlignment(Pos.CENTER);

        stage = new Stage();
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(app.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(grid));

        initNodes();
        setCloseEvent();
        stage.setOnCloseRequest(closeEventHandler);
        stage.show();
    }

    abstract void initNodes();

    abstract void setCloseEvent();
}
