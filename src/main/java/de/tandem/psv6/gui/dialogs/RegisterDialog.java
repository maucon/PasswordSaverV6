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
        super(owner,"Register", 500 * owner.getHScale(), 400 * owner.getVScale());
    }

    @Override
    void initNodes() {
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

        var passwordLabelRetype = new Label("Retype Password:");
        var passwordInputRetype = new PasswordField();
        grid.addRow(2, passwordLabelRetype, passwordInputRetype);

        okButton.setOnAction(event -> {
            // TODO proper Check
            var name = nameInput.getText();
            var password = nameInput.getText();
            if (name.isBlank() || password.isBlank()) {
                System.out.println("Wrong");
            } else if (!passwordInputRetype.getText().equals(password)) {
                new ErrorDialog(owner, "Passwords don't Match");
            } else {
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
