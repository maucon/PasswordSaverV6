package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.App;
import javafx.scene.control.Label;

public class ErrorDialog extends Dialog {

    public ErrorDialog(String errorMessage, App app) {
        super("Error", errorMessage.length() * 6 * app.getHScale(), 250 * app.getVScale(), app);
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
