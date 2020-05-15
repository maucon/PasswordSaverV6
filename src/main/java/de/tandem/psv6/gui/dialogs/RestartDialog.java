package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RestartDialog extends Dialog {

    public RestartDialog(App app) {
        super("Warning", 200 * app.getHScale(), 250 * app.getVScale(), app);
    }

    @Override
    void initNodes() {
        Label label1 = new Label("You need to Restart for the Settings to apply.");
        Label label2 = new Label("Do you want to restart now?");
        GridPane.setHalignment(label1, HPos.CENTER);
        GridPane.setHalignment(label2, HPos.CENTER);
        grid.addRow(0, label1);
        grid.addRow(1, label2);
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        HBox box = new HBox(okButton, cancelButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50 * app.getHScale());
        grid.addRow(2, box);

        okButton.setOnAction(event -> {
            stage.close();
            app.getStage().close();
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
