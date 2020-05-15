package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class LogIn {
    private final App app;
    private final Stage stage;
    private final BorderPane root;
    private EventHandler<WindowEvent> closeEventHandler;
    private final DoubleProperty hScale;
    private final DoubleProperty vScale;

    public LogIn(App app) {
        this.app = app;

        root = new BorderPane();
        root.setId("root");
        root.getStylesheets().add(app.getStyle());

        stage = new Stage();
        stage.setTitle("Log in");
        stage.setWidth(400);
        stage.setHeight(700);
        stage.initStyle(StageStyle.UTILITY);
        var scene = new Scene(root);
        stage.setScene(scene);

        hScale = new SimpleDoubleProperty();
        hScale.bind(stage.widthProperty().divide(400));
        vScale = new SimpleDoubleProperty();
        vScale.bind(stage.heightProperty().divide(700));

        initNodes();
        stage.show();
    }

    private void initNodes() {
        // Buttons:
        var loginButton = new Button("Login");
        loginButton.resize(200 * getHScale(), 100 * getVScale());
        var exitButton = new Button("Exit");
        exitButton.resize(200 * getHScale(), 100 * getVScale());

        var hBox = new HBox(loginButton, exitButton);
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(loginButton, new Insets(50 * getVScale(), 100 * getHScale(), 50 * getVScale(), 50 * getHScale()));
        HBox.setMargin(exitButton, new Insets(50 * getVScale(), 50 * getHScale(), 50 * getVScale(), 100 * getHScale()));
        root.setBottom(hBox);

        loginButton.setOnAction(event -> {
            stage.close();
            app.getStage().show();
        });
        exitButton.setOnAction(event -> stage.close());

        // Input Fields:
        var nameLabel = new Label("Name:");
        var nameInput = new TextField();
        var nameHBox = new HBox(nameLabel, nameInput);
        nameHBox.setAlignment(Pos.CENTER);
        HBox.setMargin(nameLabel, new Insets(50 * getVScale(), 100 * getHScale(), 50 * getVScale(), 50 * getHScale()));
        HBox.setMargin(nameInput, new Insets(50 * getVScale(), 50 * getHScale(), 50 * getVScale(), 100 * getHScale()));

        var passwordLabel = new Label("Password:");
        var passwordInput = new TextField();
        var passwordHBox = new HBox(passwordLabel, passwordInput);
        passwordHBox.setAlignment(Pos.CENTER);
        HBox.setMargin(passwordLabel, new Insets(50 * getVScale(), 100 * getHScale(), 50 * getVScale(), 50 * getHScale()));
        HBox.setMargin(passwordInput, new Insets(50 * getVScale(), 50 * getHScale(), 50 * getVScale(), 100 * getHScale()));

        var formVBox = new VBox(nameHBox, passwordHBox);
        formVBox.setAlignment(Pos.CENTER);
//        VBox.setMargin(nameHBox, new Insets(50 * getVScale(), 50 * getHScale(), 50 * getVScale(), 100 * getHScale()));
//        VBox.setMargin(passwordHBox, new Insets(50 * getVScale(), 50 * getHScale(), 50 * getVScale(), 100 * getHScale()));
        root.setCenter(formVBox);
    }

    private double getHScale() {
        return hScale.doubleValue();
    }

    private double getVScale() {
        return vScale.doubleValue();
    }
}
