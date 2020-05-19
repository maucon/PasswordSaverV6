package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.dialogs.EntryDialog;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Card {
    public static BorderPane createCard(App app, Entry entry) {
        var pane = new BorderPane();

        pane.setCenter(new Label(entry.getName()));
        pane.setId("Card");
        pane.setStyle(app.getStyle());
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(entry.toString());
                new EntryDialog(app, entry);
            }
        });
        pane.setPrefWidth(200 * app.getHScale());
        pane.setPrefHeight(200 * app.getVScale());

        return pane;
    }
}
