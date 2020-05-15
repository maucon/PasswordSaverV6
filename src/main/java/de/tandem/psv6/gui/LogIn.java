package de.tandem.psv6.gui;

import de.tandem.psv6.App;
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
        stage.setScene(new Scene(root));

        initNodes();
        stage.show();
    }

    private void initNodes() {
        // Buttons:
        var loginButton = new Button("Login");
        loginButton.resize(200 * app.getHScale(), 100 * app.getVScale());
        var exitButton = new Button("Exit");
        exitButton.resize(200 * app.getHScale(), 100 * app.getVScale());

        var hBox = new HBox(loginButton, exitButton);
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(loginButton, new Insets(50, 100, 50, 50));
        HBox.setMargin(exitButton, new Insets(50, 50, 50, 100));
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

        var passwordLabel = new Label("Password:");
        var passwordInput = new TextField();
        var passwordHBox = new HBox(passwordLabel, passwordInput);

        var formVBox = new VBox(nameHBox, passwordHBox);
        root.setCenter(formVBox);
    }
}
