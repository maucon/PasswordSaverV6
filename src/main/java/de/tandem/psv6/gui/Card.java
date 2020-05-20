package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.dialogs.CreateEntryDialog;
import de.tandem.psv6.gui.dialogs.EntryDialog;
import de.tandem.psv6.gui.dialogs.RemoveConfirmDialog;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Card {
    public static BorderPane createCard(App app, Entry entry) {
        var label = new Label(entry.getName());
        label.setWrapText(true);
        var pane = new BorderPane();
        pane.setPadding(new Insets(10 * app.getHScale()));
        pane.setCenter(label);
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
        var img = new ImageView(new Image("img/plus sign.png"));
        img.setFitWidth(70 * app.getHScale());
        img.setFitHeight(70 * app.getVScale());
        var pane = new BorderPane();
        pane.setCenter(img);
        pane.setId("Card");
        pane.setStyle(app.getStyle());
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> new CreateEntryDialog(app));
        pane.setPrefWidth(200 * app.getHScale());
        pane.setPrefHeight(200 * app.getVScale());
        return pane;
    }
}
