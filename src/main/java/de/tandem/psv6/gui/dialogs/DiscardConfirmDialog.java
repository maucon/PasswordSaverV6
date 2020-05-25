package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.gui.GUIOwner;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DiscardConfirmDialog extends Dialog {

    public DiscardConfirmDialog(GUIOwner owner, Stage ownerStage) {
        super(owner, ownerStage, "Discard changes", 400, 200);
        grid.addRow(0, new Label("You have some unsaved changes.\n               Discard them?"));
        addOkCancelButtons(1, 100).onActionProperty().set(event -> {
            stage.close();
            ownerStage.close();
        });
    }
}
