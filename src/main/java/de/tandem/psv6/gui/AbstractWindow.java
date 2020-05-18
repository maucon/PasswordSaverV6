package de.tandem.psv6.gui;

import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class AbstractWindow {
    protected GridPane grid;
    protected Stage stage;
    protected GUIOwner owner;

    protected TextInputControl addField(int gridIndex, String labelText) {
        return addField(gridIndex, labelText, false);
    }

    protected TextInputControl addField(int gridIndex, String labelText, boolean isPassword) {
        var label = new Label(labelText);
        var input = isPassword ? new PasswordField() : new TextField();
        grid.addRow(gridIndex, label, input);
        return input;
    }

    protected Button addOkCancelButtons(int gridIndex) {
        var okButton = new Button("OK");
        var cancelButton = new Button("Cancel");
        grid.addRow(gridIndex, okButton, cancelButton);
        GridPane.setHalignment(okButton, HPos.CENTER);
        GridPane.setHalignment(cancelButton, HPos.CENTER);
        cancelButton.setOnAction(event -> stage.close());
        return okButton;
    }

    protected void addLabel(int gridIndex, String content) {
        var label = new Label(content);
        GridPane.setHalignment(label, HPos.CENTER);
        grid.addRow(gridIndex, label);

    }
}
