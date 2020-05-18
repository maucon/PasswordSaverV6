package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.User;
import de.tandem.psv6.gui.GUIOwner;
import de.tandem.psv6.security.Security;
import javafx.scene.control.ComboBox;

public class RegisterDialog extends Dialog {
    private final ComboBox<String> comboBox;

    public RegisterDialog(GUIOwner owner, ComboBox<String> comboBox) {
        super(owner, "Register", 500, 400);
        this.comboBox = comboBox;
    }

    @Override
    protected void initNodes() {
        grid.setHgap(25 * owner.getHScale());
        grid.setVgap(10 * owner.getVScale());

        // Input Fields:
        var nameInput = addField(0, "Name:");
        var passwordInput = addField(1, "Password:", true);
        var password2Input = addField(2, "Retype Password:", true);

        addOkCancelButtons(3).setOnAction(event -> {
            var name = nameInput.getText();
            var password = passwordInput.getText();
            var password2 = password2Input.getText();

            if (name.isBlank()) new ErrorDialog(owner, stage, "Name is blank.");
            else if (password.isBlank()) new ErrorDialog(owner, stage, "Password is blank.");
            else if (!password.equals(password2)) new ErrorDialog(owner, stage, "Passwords don't match.");
            else {
                Database.setupUser(new User(name, Security.save_hash(name, password)));
                comboBox.getItems().clear();
                comboBox.getItems().addAll(Database.getUserList());
                stage.close();
            }
        });
    }
}
