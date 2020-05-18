package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.gui.GUIOwner;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegisterDialog extends Dialog {
    public RegisterDialog(GUIOwner owner) {
        super(owner, "Register", 500, 400);
    }

    @Override
    void initNodes() {
        grid.setHgap(25 * owner.getHScale());
        grid.setVgap(10 * owner.getVScale());

        var okButton = new Button("OK");
        var cancelButton = new Button("Cancel");
        var box = new HBox(okButton, cancelButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        grid.addRow(3, box);

        // Input Fields:
        var nameLabel = new Label("Name:");
        var nameInput = new TextField();
        grid.addRow(0, nameLabel, nameInput);

        var passwordLabel = new Label("Password:");
        var passwordInput = new PasswordField();
        grid.addRow(1, passwordLabel, passwordInput);

        var password2Label = new Label("Retype Password:");
        var password2Input = new PasswordField();
        grid.addRow(2, password2Label, password2Input);

        okButton.setOnAction(event -> {
            // TODO proper Check
            var name = nameInput.getText();
            var password = passwordInput.getText();
            var password2 = password2Input.getText();

            if (name.isBlank()) new ErrorDialog(owner, stage, "Name is blank.");
            else if (password.isBlank()) new ErrorDialog(owner, stage, "Password is blank.");
            else if (!password.equals(password2)) new ErrorDialog(owner, stage, "Passwords don't match.");
            else {
                stage.close();
            }
        });
        cancelButton.setOnAction(event -> stage.close());
    }

    @Override
    void setCloseEvent() {
        closeEventHandler = windowEvent -> stage.close();
    }
}
