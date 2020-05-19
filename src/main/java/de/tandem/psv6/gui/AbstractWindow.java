package de.tandem.psv6.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

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

    protected ComboBox<String> addCombo(int gridIndex, String labelText, List<String> items) {
        var label = new Label(labelText);
        var input = new ComboBox<String>();
        input.getItems().addAll(items);
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

    protected Button addOkCancelButtons(int gridIndex, boolean hBox, double width) {
        if (!hBox) return addOkCancelButtons(gridIndex);
        var okButton = new Button("OK");
        var cancelButton = new Button("Cancel");
        var subGrid = new GridPane();
        subGrid.addRow(0, okButton, cancelButton);
        subGrid.setId("gridPane");
        subGrid.getStylesheets().add(owner.getStyle());
        subGrid.setAlignment(Pos.CENTER);
        subGrid.hgapProperty().set(width * owner.getHScale());
        GridPane.setHalignment(okButton, HPos.CENTER);
        GridPane.setHalignment(cancelButton, HPos.CENTER);
        grid.addRow(gridIndex, subGrid);
        GridPane.setHalignment(subGrid, HPos.CENTER);
        cancelButton.setOnAction(event -> stage.close());
        return okButton;
    }

    protected void addLabel(int gridIndex, String content) {
        var label = new Label(content);
        GridPane.setHalignment(label, HPos.CENTER);
        grid.addRow(gridIndex, label);

    }
}
