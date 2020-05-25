package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import javafx.scene.control.Label;

public class RemoveConfirmDialog extends Dialog {

    public RemoveConfirmDialog(App app, Entry entry) {
        super(app, "Remove?", 400, 200);
        grid.addRow(0, new Label("Are you sure you want to remove\n" + entry.getName() + "?"));
        addOkCancelButtons(1, 100).onActionProperty().set(event -> {
            app.removeEntry(entry);
            Database.getInstance().removeEntry(entry);
            stage.close();
        });
    }

}
