package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.gui.dialogs.ErrorDialog;
import de.tandem.psv6.gui.dialogs.RegisterDialog;
import de.tandem.psv6.security.Security;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.identityconnectors.common.security.GuardedString;


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

        var nameInput = addCombo(0, "Name:", Database.getUserList());
        var passwordInput = addField(1, "Password:", true);
        nameInput.setPrefWidth(150 * getHScale());

        // Event Listeners:
        loginButton.setOnAction(event -> {
            var name = nameInput.getSelectionModel().getSelectedItem();
            var password = passwordInput.getText();

            if (name == null) new ErrorDialog(this, "Name not selected.");
            else if (password.isBlank()) new ErrorDialog(this, "Password is blank.");
            else if (!Security.passwordMatches(name, password))
                new ErrorDialog(this, "Username or Password is incorrect.");
            else {
                Database.createInstance(name).loadUserSettings();
                Security.guardedString = new GuardedString(Security.key_hash(password).toCharArray());
                app.updateStyle();
                stage.close();
                new MenuBar(app);
                app.addNode(Card.addEntryCard(app));
                for (var e : Database.getInstance().getAllEntries()) app.addNode(Card.createCard(app, e));
                app.getStage().show();
            }
        });
        registerButton.setOnAction(event -> new RegisterDialog(this, nameInput));
        passwordInput.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) loginButton.fire();
            }
        });
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
