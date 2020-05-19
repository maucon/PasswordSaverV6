package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.Card;
import javafx.scene.control.TextInputControl;

public class CreateEntryDialog extends Dialog {
    private TextInputControl nameField;
    private TextInputControl loginField;
    private TextInputControl passwordField;
    private TextInputControl descriptionField;

    public CreateEntryDialog(App app) {
        super(app, "Create new", 450, 300);
    }

    @Override
    protected void initNodes() {
        grid.setVgap(15 * owner.getVScale());
        grid.setHgap(5 * owner.getHScale());
        nameField = addField(0, "Name:");
        loginField = addField(1, "Login:");
        passwordField = addField(2, "Password:", true);
        descriptionField = addField(3, "Description:");
        addOkCancelButtons(4).onActionProperty().set(event -> {
            if (!nameField.getText().isBlank() || !loginField.getText().isBlank() || !passwordField.getText().isBlank()) {
                if (nameField.getText().isBlank() || loginField.getText().isBlank() || passwordField.getText().isBlank()) {
                    new ErrorDialog(owner, stage, "Not all required Fields are filled in.");
                    return;
                }
                var entry = new Entry(nameField.getText(), loginField.getText(), passwordField.getText(), descriptionField.getText(), null);
                Database.getInstance().addEntry(entry);
                ((App) owner).addNode(Card.createCard(((App) owner), entry));
                stage.close();
            }
        });
    }
}
