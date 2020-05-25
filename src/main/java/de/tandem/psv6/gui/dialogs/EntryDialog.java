package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.GUIOwner;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class EntryDialog extends Dialog {
    private final Entry entry;
    private TextInputControl nameField;
    private TextInputControl passwordField;
    private TextInputControl descriptionField;

    public EntryDialog(GUIOwner owner, Entry entry) {
        super(owner, entry.getName(), 500, 270);
        this.entry = entry;
        nameField.setText(entry.getLogin());
        passwordField.setText(entry.getPassword());
        descriptionField.setText(entry.getDescription());
    }

    @Override
    protected void initNodes() {
        grid.setHgap(25 * owner.getHScale());
        grid.setVgap(10 * owner.getVScale());

        // Input Fields:
        nameField = addField(1, "Name:");

        var copyNameButton = new Button();
        var plusImg = new ImageView(new Image("img/copy-solid " + (Settings.darkMode ? "dark" : "light") + ".png"));
        plusImg.setFitWidth(20 * owner.getHScale());
        plusImg.setFitHeight(20 * owner.getVScale());
        copyNameButton.setGraphic(plusImg);
        grid.add(copyNameButton, 2, 1);

        passwordField = addField(2, "Password:", true);

        var copyPasswordButton = new Button();
        var pepe = new ImageView(new Image("img/copy-solid " + (Settings.darkMode ? "dark" : "light") + ".png"));
        pepe.setFitWidth(20 * owner.getHScale());
        pepe.setFitHeight(20 * owner.getVScale());
        copyPasswordButton.setGraphic(pepe);
        grid.add(copyPasswordButton, 2, 2);

        descriptionField = addField(3, "Description:");

        var okButton = addOkCancelButtons(4, 100);
        okButton.setText("Save");
        okButton.onActionProperty().set(e -> {
            entry.setLogin(nameField.getText());
            entry.setPassword(passwordField.getText());
            entry.setDescription(descriptionField.getText());
            Database.getInstance().editEntry(entry);
            stage.close();
        });

        copyNameButton.onActionProperty().set(e -> {
            var clipboard = new ClipboardContent();
            clipboard.putString(nameField.getText());
            Clipboard.getSystemClipboard().setContent(clipboard);
        });
        copyPasswordButton.onActionProperty().set(e -> {
            var clipboard = new ClipboardContent();
            clipboard.putString(passwordField.getText());
            Clipboard.getSystemClipboard().setContent(clipboard);
        });
    }

    @Override
    protected void setCloseEvent() {
        closeEventHandler = windowEvent -> {
            if (!nameField.getText().equals(entry.getLogin()) || !passwordField.getText().equals(entry.getPassword()) || !descriptionField.getText().equals(entry.getDescription())) {
                windowEvent.consume();
                new SaveConfirmDialog(owner, stage, entry, nameField.getText(), passwordField.getText(), descriptionField.getText());
            }
        };
    }
}
