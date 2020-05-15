package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBar {
    private final App app;
    private final javafx.scene.control.MenuBar menuBar;
    private final Menu[] menus;
    private final MenuItem[][] items;

    public MenuBar(App app) {
        this.app = app;
        menus = new Menu[]{new Menu("_File"), new Menu("_Edit"), new Menu("_Items & Categories"), new Menu("_Options")};
        items = new MenuItem[menus.length][];
        items[0] = new MenuItem[]{new MenuItem("_New"), new MenuItem("_Export"), new MenuItem("_Save"), new MenuItem("Save _As..."), new MenuItem("_Load"), new MenuItem("_Load Excel"), new MenuItem("_Exit")};
        items[1] = new MenuItem[]{new MenuItem("_Refresh"), new MenuItem("Edit _Item"), new MenuItem("Edit _Category")};
        items[2] = new MenuItem[]{new MenuItem("New _Item"), new MenuItem("New _Category"), new MenuItem("_Remove Item"), new MenuItem("_Remove Category")};
        items[3] = new MenuItem[]{new CheckMenuItem("_Collapse Categories"), new CheckMenuItem("_Dark Mode")};
        addListeners();
        for (int i = 0; i < menus.length; i++) {
            menus[i].getItems().addAll(items[i]);
        }
        menuBar = new javafx.scene.control.MenuBar(menus);
        app.getRoot().setTop(menuBar);
    }

    private void addListeners() {
//        items[0][4].setOnAction(event -> new LoadDialog("Load", app));
    }

    private void setMnemonics() {
        for (Menu m : menus) m.setMnemonicParsing(true);
        for (MenuItem[] iArr : items) for (MenuItem i : iArr) i.setMnemonicParsing(true);
    }
}
