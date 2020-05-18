package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.gui.GUIOwner;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RestartDialog extends Dialog {

    public RestartDialog(GUIOwner owner) {
        super(owner, "Warning", 200 * owner.getHScale(), 250 * owner.getVScale());
    }

    @Override
    void initNodes() {
        var label1 = new Label("You need to Restart for the Settings to apply.");
        var label2 = new Label("Do you want to restart now?");
        GridPane.setHalignment(label1, HPos.CENTER);
        GridPane.setHalignment(label2, HPos.CENTER);
        grid.addRow(0, label1);
        grid.addRow(1, label2);
        var okButton = new Button("OK");
        var cancelButton = new Button("Cancel");
        var box = new HBox(okButton, cancelButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50 * owner.getHScale());
        grid.addRow(2, box);

        okButton.setOnAction(event -> {
            stage.close();
            owner.getStage().close();
            Platform.runLater(() -> {
                try {
                    new App().start(new Stage());
                } catch (Exception ignored) {
                }
            });
        });
        cancelButton.setOnAction(event -> stage.close());
    }

    @Override
    void setCloseEvent() {
        closeEventHandler = windowEvent -> {
        };
    }
}
