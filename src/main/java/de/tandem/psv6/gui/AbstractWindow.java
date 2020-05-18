package de.tandem.psv6.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public abstract class AbstractWindow {
    protected GridPane grid;
    protected Stage stage;
    protected GUIOwner owner;

    protected TextInputControl addField(String labelText, int gridIndex) {
        return addField(labelText, gridIndex, false);
    }

    protected TextInputControl addField(String labelText, int gridIndex, boolean isPassword) {
        var label = new Label(labelText);
        var input = isPassword ? new PasswordField() : new TextField();
        grid.addRow(gridIndex, label, input);
        return input;
    }

    protected Button addOkCancelButtons(int gridIndex) {
        var okButton = new Button("OK");
        var cancelButton = new Button("Cancel");
        var box = new HBox(okButton, cancelButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50 * owner.getHScale());
        grid.addRow(gridIndex, box);
        cancelButton.setOnAction(event -> stage.close());
        return okButton;
    }

    protected void addLabel(int gridIndex, String content) {
        var label = new Label(content);
        GridPane.setHalignment(label, HPos.CENTER);
        grid.addRow(gridIndex, label);

    }
}
