package de.tandem.psv6.gui;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Converter;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.gui.dialogs.CreateEntryDialog;
import de.tandem.psv6.gui.dialogs.RestartDialog;
import javafx.scene.control.*;

public class MenuBar {

    private final App app;
    private final MenuItem[][] items;

    public MenuBar(App app) {
        this.app = app;

        var lenMenu = new Menu("Password-length");
        int[] lenValues = {6, 8, 12, 16, 24, 32, 64, 96, 128};
        var toggleGroup = new ToggleGroup();
        for (int i : lenValues) {
            var item = new RadioMenuItem(i + "");
            item.setToggleGroup(toggleGroup);
            item.setOnAction(event -> {
                Settings.passwordLength = Integer.parseInt(item.getText());
                Database.getInstance().saveUserSettings();
            });
            if (item.getText().equals(Settings.passwordLength + "")) item.setSelected(true);
            lenMenu.getItems().add(item);
        }

        Menu pwMenu = new Menu("Password-Generator");
        pwMenu.getItems().addAll(lenMenu, new CheckMenuItem("Uppercase Letter"), new CheckMenuItem("Numbers"), new CheckMenuItem("Symbols"));
        pwMenu.getItems().get(1).setOnAction(event -> {
            Settings.useUppercaseLetters = !Settings.useUppercaseLetters;
            Database.getInstance().saveUserSettings();
        });
        ((CheckMenuItem) pwMenu.getItems().get(1)).setSelected(Settings.useUppercaseLetters);
        pwMenu.getItems().get(2).setOnAction(event -> {
            Settings.useNumbers = !Settings.useNumbers;
            Database.getInstance().saveUserSettings();
        });
        ((CheckMenuItem) pwMenu.getItems().get(2)).setSelected(Settings.useNumbers);
        pwMenu.getItems().get(3).setOnAction(event -> {
            Settings.useSymbols = !Settings.useSymbols;
            Database.getInstance().saveUserSettings();
        });
        ((CheckMenuItem) pwMenu.getItems().get(3)).setSelected(Settings.useSymbols);

        Menu[] menus = new Menu[]{new Menu("_File"), new Menu("_Options")};
        items = new MenuItem[menus.length][];
        items[0] = new MenuItem[]{new MenuItem("_New"), new MenuItem("_Import"), new MenuItem("Exit")};
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
        items[0][2].setOnAction(e -> app.getStage().close());
        items[0][1].setOnAction(e -> Converter.convert(app));
        items[0][0].setOnAction(e -> new CreateEntryDialog(app));
    }

}
