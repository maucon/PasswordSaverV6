package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.gui.GUIOwner;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ErrorDialog extends Dialog {

    public ErrorDialog(GUIOwner owner, String errorMessage) {
        this(owner, owner.getStage(), errorMessage);
    }

    public ErrorDialog(GUIOwner owner, Stage ownerStage, String errorMessage) {
        super(owner, ownerStage, "Error", errorMessage.length() * 13, 200);
        grid.addRow(0, new Label(errorMessage));
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) stage.close();
        });
    }

}
