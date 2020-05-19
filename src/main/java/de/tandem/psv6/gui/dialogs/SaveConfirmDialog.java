package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.GUIOwner;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SaveConfirmDialog extends Dialog {

    public SaveConfirmDialog(GUIOwner owner, Stage ownerStage, Entry entry, String newLogin, String newPassword, String newDescription) {
        super(owner, ownerStage, "Confirm Changes", 400, 200);
        grid.addRow(0, new Label("You have some unsaved changes. Save now?"));
        addOkCancelButtons(1, true, 100).onActionProperty().set(event -> {
            entry.setLogin(newLogin);
            entry.setPassword(newPassword);
            entry.setDescription(newDescription);
            Database.getInstance().editEntry(entry);
            stage.close();
            ownerStage.close();
        });
        ((Button) ((GridPane) grid.getChildren().get(1)).getChildren().get(1)).onActionProperty().set(e -> {
            stage.close();
            ownerStage.close();
        });
    }
}
