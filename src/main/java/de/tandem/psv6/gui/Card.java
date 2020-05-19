package de.tandem.psv6.gui;

import de.tandem.psv6.entity.Entry;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Card {
    public static BorderPane createCard(GUIOwner owner, Entry entry) {
        var pane = new BorderPane();

        pane.setCenter(new Label(entry.getName()));
        pane.setId("Card");
        pane.setStyle(owner.getStyle());
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(entry.toString());
            }
        });

        return pane;
    }
}
