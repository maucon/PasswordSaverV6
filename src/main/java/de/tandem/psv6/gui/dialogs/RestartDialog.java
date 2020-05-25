package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.gui.GUIOwner;
import javafx.application.Platform;
import javafx.stage.Stage;

public class RestartDialog extends Dialog {

    public RestartDialog(GUIOwner owner) {
        super(owner, "Warning", 400, 250);
    }

    @Override
    protected void initNodes() {
        addLabel(0, "You need to Restart for the Settings to apply.");
        addLabel(1, "Do you want to restart now?");

        addOkCancelButtons(2, 70).setOnAction(event -> {
            stage.close();
            owner.getStage().close();
            Platform.runLater(() -> {
                try {
                    new App().start(new Stage());
                } catch (Exception ignored) {
                }
            });
        });
    }
}
