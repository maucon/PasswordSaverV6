package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.GUIOwner;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class EntryDialog extends Dialog {
    private final Entry entry;
    private TextInputControl nameField;
    private TextInputControl passwordField;

    public EntryDialog(GUIOwner owner, Entry entry) {
        super(owner, entry.getName(), 500, 400);
        this.entry = entry;
        nameField.setText(entry.getLogin());
        passwordField.setText(entry.getPassword());
    }

    @Override
    protected void initNodes() {
        grid.setHgap(25 * owner.getHScale());
        grid.setVgap(10 * owner.getVScale());

        // Input Fields:
        nameField = addField(0, "Name:");
        var copyNameButton = new Button("Copy");
        grid.add(copyNameButton, 2, 0);

        passwordField = addField(1, "Password:", true);
        var copyPasswordButton = new Button("Copy");
        grid.add(copyPasswordButton, 2, 1);

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
            if (!nameField.getText().equals(entry.getLogin()) || !passwordField.getText().equals(entry.getPassword())) {
                windowEvent.consume();
                new SaveConfirmDialog(owner, stage, entry, nameField.getText(), passwordField.getText());
            }
        };
    }
}