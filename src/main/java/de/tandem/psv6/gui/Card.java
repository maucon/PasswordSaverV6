package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.dialogs.CreateEntryDialog;
import de.tandem.psv6.gui.dialogs.EntryDialog;
import de.tandem.psv6.gui.dialogs.RemoveConfirmDialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Card {
    public static BorderPane createCard(App app, Entry entry) {
        var pane = new BorderPane();
        pane.setCenter(new Label(entry.getName()));
        pane.setId("Card");
        pane.setStyle(app.getStyle());
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) new EntryDialog(app, entry);
            if (e.getButton() == MouseButton.SECONDARY) new RemoveConfirmDialog(app, entry);
        });

        pane.setPrefWidth(200 * app.getHScale());
        pane.setPrefHeight(200 * app.getVScale());
        return pane;
    }

    public static BorderPane addEntryCard(App app) {
        var pane = new BorderPane();
        pane.setCenter(new Label("+"));
        pane.setId("Card");
        pane.setStyle(app.getStyle());
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> new CreateEntryDialog(app));
        pane.setPrefWidth(200 * app.getHScale());
        pane.setPrefHeight(200 * app.getVScale());
        return pane;
    }
}
