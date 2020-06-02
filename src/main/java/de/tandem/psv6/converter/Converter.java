package de.tandem.psv6.converter;

import de.tandem.psv6.App;
import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.gui.dialogs.ErrorDialog;
import de.tandem.psv6.security.Security;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

public class Converter {

    private static final String CONVERT_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().toString() + "/PasswordSaverV6/";
    private static final String OLD_ENTRIES_PATH = CONVERT_PATH + "old/";
    private static final String NEW_ENTRIES_PATH = CONVERT_PATH + "new/";

    public static void convert(App app) {
        File folder = new File(OLD_ENTRIES_PATH);
        if (!folder.exists()) {
            folder.mkdir();
            new ErrorDialog(app, "No entries found!");
            return;
        }
        new File(NEW_ENTRIES_PATH).mkdir();

        int failCounter = 0, successCounter = 0;
        for (File fileEntry : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".mss")))) {
            try (var bufferedReader = new BufferedReader(new FileReader(fileEntry));
                 var out = new ObjectOutputStream(Security.encryptStream(
                         new FileOutputStream(NEW_ENTRIES_PATH + new SimpleDateFormat(Database.ENTRY_FILE_NAME_FORMAT).format(new Date()) + Database.ENTRY_FILE_EXTENSION),
                         Security.accessGuardedKey(Security.guardedString)))) {
                out.writeObject(new Entry(fileEntry.getName().substring(0, fileEntry.getName().length() - 4), readLogin(bufferedReader.readLine()), readPassword(bufferedReader.readLine()), "", ""));
                successCounter++;
            } catch (IOException | GeneralSecurityException | NullPointerException ignored) {
                failCounter++;
                System.out.println(fileEntry.getName() + " is corrupted!");
            }
        }

        new ErrorDialog(app, successCounter + " entries got converted.\n" + failCounter + " failed to convert.");
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
        return new String(Base64.getDecoder().decode(e));
    }

}
