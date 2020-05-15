package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class LogIn {
    private final App app;
    private final Stage stage;
    private final GridPane grid;
    private EventHandler<WindowEvent> closeEventHandler;

    public LogIn(App app) {
        this.app = app;

        grid = new GridPane();
        grid.setId("gridPane");
        grid.getStylesheets().add(app.getStyle());
        grid.setAlignment(Pos.CENTER);

        stage = new Stage();
        stage.setTitle("Log in");
        stage.setWidth(400);
        stage.setHeight(700);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(grid));

        initNodes();
        setCloseEvent();
        stage.setOnCloseRequest(closeEventHandler);
        stage.show();
    }

    private void initNodes() {
        Button loginButton = new Button("Login");
        grid.addRow(0, loginButton);
        loginButton.setOnAction(event -> {
            stage.close();
            app.getStage().show();
        });
    }

    private void setCloseEvent() {
        closeEventHandler = windowEvent -> {
            app.getStage().close();
        };
    }
}
