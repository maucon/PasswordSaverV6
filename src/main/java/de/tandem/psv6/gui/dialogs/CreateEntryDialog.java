package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.Card;
import de.tandem.psv6.security.PasswordGenerator;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CreateEntryDialog extends Dialog {

    private static final int MAX_NAME_LENGTH = 20;

    private TextInputControl nameField;
    private TextInputControl loginField;
    private TextInputControl passwordField;
    private TextInputControl descriptionField;

    public CreateEntryDialog(App app) {
        super(app, "Create new", 450, 320);
    }

    @Override
    protected void initNodes() {
        grid.setVgap(15 * owner.getVScale());
        grid.setHgap(5 * owner.getHScale());
        nameField = addField(0, "Name:");
        nameField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (nameField.getText().length() > MAX_NAME_LENGTH) {
                String s = nameField.getText().substring(0, MAX_NAME_LENGTH);
                nameField.setText(s);
            }
        });
        loginField = addField(1, "Login:");
        passwordField = addField(2, "Password:");
        var generatePassword = new Button();
        var plusImg = new ImageView(new Image("img/Generate Password small " + (Settings.darkMode ? "dark" : "light") + ".png"));
        plusImg.setFitWidth(20 * owner.getHScale());
        plusImg.setFitHeight(20 * owner.getVScale());
        generatePassword.setGraphic(plusImg);
        grid.add(generatePassword, 3, 2);
        generatePassword.onActionProperty().set(e -> passwordField.setText(PasswordGenerator.create()));

        descriptionField = addField(3, "Description:");
        var okbutton = addOkCancelButtons(4, 100);
        okbutton.onActionProperty().set(event -> {
            if (nameField.getText().isBlank() || loginField.getText().isBlank() || passwordField.getText().isBlank()) {
                new ErrorDialog(owner, stage, "Not all required fields are filled in.");
                return;
            }
            if (!Entry.isAllowedString(nameField.getText()) || !Entry.isAllowedString(loginField.getText()) || !Entry.isAllowedString(passwordField.getText()) || !Entry.isAllowedString(descriptionField.getText())) {
                new ErrorDialog(owner, stage, "Fields contain illegal character.");
                return;
            }
            var entry = new Entry(nameField.getText(), loginField.getText(), passwordField.getText(), descriptionField.getText(), null);
            ((App) owner).addNode(Card.createCard(((App) owner), Database.getInstance().addEntry(entry)));
            stage.close();
        });

        var handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) okbutton.fire();
            }
        };
        nameField.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        loginField.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        passwordField.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        descriptionField.addEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

}
