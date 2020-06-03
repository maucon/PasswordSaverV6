package de.tandem.psv6.database;

import de.tandem.psv6.App;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.Card;
import de.tandem.psv6.gui.dialogs.ErrorDialog;
import javafx.stage.DirectoryChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

public class Converter {

    public static void convert(App app) {
        int failCounter = 0;
        for (File fileEntry : Objects.requireNonNull(new DirectoryChooser().showDialog(app.getStage()).listFiles((dir, name) -> name.endsWith(".mss")))) {
            try (var bufferedReader = new BufferedReader(new FileReader(fileEntry))) {

                Entry entry = new Entry(fileEntry.getName().substring(0, fileEntry.getName().length() - 4), readLogin(bufferedReader.readLine()), readPassword(bufferedReader.readLine()), "", "");
                app.addNode(Card.createCard((app), Database.getInstance().addEntry(entry)));

            } catch (Exception ignored) {
                failCounter++;
            }
        }

        if (failCounter > 0) new ErrorDialog(app, failCounter + " entries failed to convert.");
    }

    private static String readLogin(String line) {
        line = decode(line);
        return line.substring(172, line.length() - 191);
    }

    private static String readPassword(String line) {
        line = decode(line);
        return line.substring(81, line.length() - 326);
    }

    private static String decode(final String e) {
        return new String(DatatypeConverter.parseBase64Binary(e));
    }

}
