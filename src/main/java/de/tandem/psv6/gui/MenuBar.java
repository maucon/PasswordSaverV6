package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.dialogs.RestartDialog;
import javafx.scene.control.*;

public class MenuBar {

    private final App app;
    private final MenuItem[][] items;

    public MenuBar(App app) {
        this.app = app;
        Menu lenMenu = new Menu("Password-length");
        //lenMenu.getItems().addAll()

        Menu pwMenu = new Menu("Password-Generator");
        pwMenu.getItems().addAll(new CheckMenuItem("Uppercase Letter"), new CheckMenuItem("Numbers"), new CheckMenuItem("Symbols"));


        Menu[] menus = new Menu[]{new Menu("_File"), new Menu("_Options")};
        items = new MenuItem[menus.length][];
        items[0] = new MenuItem[]{new MenuItem("_New"), new MenuItem("Exit")};
        items[1] = new MenuItem[]{new CheckMenuItem("_Dark Mode"), pwMenu};
        addListeners();

        for (int i = 0; i < menus.length; i++) menus[i].getItems().addAll(items[i]);
        javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar(menus);
        app.getRoot().setTop(menuBar);
    }

    private void addListeners() {
        ((CheckMenuItem) items[1][0]).setSelected(Settings.darkMode);
        items[1][0].setOnAction(event -> {
            Settings.darkMode = !Settings.darkMode;
            Database.getInstance().saveUserSettings();
            new RestartDialog(app);
        });
        items[0][1].setOnAction(event -> app.getStage().close());
    }

}
