package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.gui.GUIOwner;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorDialog extends Dialog {

    public ErrorDialog(GUIOwner owner, String errorMessage) {
        this(owner, owner.getStage(), errorMessage);
    }

    public ErrorDialog(GUIOwner owner, Stage ownerStage, String errorMessage) {
        super(owner, ownerStage, "Error", errorMessage.length() * 13, 200);
        grid.addRow(0, new Label(errorMessage));
    }

    @Override
    void initNodes() {
    }

    @Override
    void setCloseEvent() {
        closeEventHandler = windowEvent -> {
        };
    }
}
