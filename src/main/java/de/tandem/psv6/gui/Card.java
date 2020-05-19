package de.tandem.psv6.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Card {
    public static BorderPane createCard(GUIOwner owner) {
        var pane = new BorderPane();

//        pane.setCenter(new Image("URL"));
        pane.setCenter(new Label("IMAGE"));
        pane.setId("Card");
        pane.setStyle(owner.getStyle());

        return pane;
    }
}
