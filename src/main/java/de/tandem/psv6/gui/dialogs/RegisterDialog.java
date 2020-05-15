package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegisterDialog extends Dialog {

    public RegisterDialog(App app, Stage owner, double width, double height) {
        super("Register", width, height, app, owner);
    }

    @Override
    void initNodes() {
        var okButton = new Button("OK");
        var cancelButton = new Button("Cancel");
        var box = new HBox(okButton, cancelButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        grid.addRow(0, box);

        okButton.setOnAction(event -> {
            stage.close();
        });
        cancelButton.setOnAction(event -> stage.close());
    }

    @Override
    void setCloseEvent() {
        closeEventHandler = windowEvent -> stage.close();
    }
}
