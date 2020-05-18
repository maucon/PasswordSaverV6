package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.gui.dialogs.ErrorDialog;
import de.tandem.psv6.gui.dialogs.RegisterDialog;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class LogIn extends AbstractWindow implements GUIOwner {
    private final App app;
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
        var scene = new Scene(root);
        stage.setScene(scene);

        vScale.bind(stage.heightProperty().divide(700));
        hScale.bind(stage.widthProperty().divide(400));
        System.out.println(getHScale());

        initNodes();
        stage.show();
    }

    private void initNodes() {
        // Buttons:
        var loginButton = new Button("Login");
        loginButton.resize(200 * getHScale(), 100 * getVScale());
        var registerButton = new Button("Register");
        registerButton.resize(200 * getHScale(), 100 * getVScale());

        var hBox = new HBox(registerButton, loginButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(80 * getHScale());
        hBox.setMinHeight(60 * getVScale());
        root.setBottom(hBox);

        // Input Fields:
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(25 * getHScale());
        grid.setVgap(10 * getVScale());
        root.setCenter(grid);

        var nameInput = addField(0, "Name:");
        var passwordInput = addField(1, "Password:", true);

        // Event Listeners:
        loginButton.setOnAction(event -> {
            var name = nameInput.getText();
            var password = nameInput.getText();

            if (name.isBlank()) new ErrorDialog(this, "Name is blank.");
            else if (password.isBlank()) new ErrorDialog(this, "Password is blank.");
//            else if () new ErrorDialog(this, "Username or Passowrd is incorrect."); // TODO proper check
            else {
                stage.close();
                app.getStage().show();
            }
        });
        registerButton.setOnAction(event -> new RegisterDialog(this));
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public String getStyle() {
        return app.getStyle();
    }
}
