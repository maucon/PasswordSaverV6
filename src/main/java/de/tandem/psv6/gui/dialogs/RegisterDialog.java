package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.gui.GUIOwner;

public class RegisterDialog extends Dialog {

    public RegisterDialog(GUIOwner owner) {
        super(owner, "Register", 500, 400);
    }

    @Override
    protected void initNodes() {
        grid.setHgap(25 * owner.getHScale());
        grid.setVgap(10 * owner.getVScale());

        // Input Fields:
        var nameInput = addField("Name:", 0);
        var passwordInput = addField("Password:", 1, true);
        var password2Input = addField("Retype Password:", 2, true);

        addOkCancelButtons(3).setOnAction(event -> {
            var name = nameInput.getText();
            var password = passwordInput.getText();
            var password2 = password2Input.getText();

            if (name.isBlank()) new ErrorDialog(owner, stage, "Name is blank.");
            else if (password.isBlank()) new ErrorDialog(owner, stage, "Password is blank.");
            else if (!password.equals(password2)) new ErrorDialog(owner, stage, "Passwords don't match.");
            else {
                // TODO save User
                stage.close();
            }
        });
    }
}
