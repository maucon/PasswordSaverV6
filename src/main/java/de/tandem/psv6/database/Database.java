package de.tandem.psv6.database;

import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.entity.User;
import de.tandem.psv6.exceptions.UserAlreadyExistsException;
import de.tandem.psv6.security.Security;
import org.identityconnectors.common.security.GuardedString;

import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Database {

    private static final String DATABASE_PATH = "database/";
    private static final String PASSWORD_FILE_NAME = "user.key";
    private static final String ENTRY_FOLDER_NAME = "entries/";
    private static final String BACKUP_FOLDER_NAME = "backups/";
    private static final String ENTRY_FILE_EXTENSION = ".entry";
    private static final String CONFIG_FILE_NAME = "config.cfg";

    private final String userPath;

    public Database(String username) {
        this.userPath = DATABASE_PATH + username + "/";
    }

    // ------------------------- STATIC -------------------------
    public static Database setupUser(User user) {
        String userPath = DATABASE_PATH + user.getUsername() + "/";

        if (!new File(userPath).mkdir())
            throw new UserAlreadyExistsException();

        try (var bufferedWriter = new BufferedWriter(new FileWriter(new File(userPath + PASSWORD_FILE_NAME)))) {

            new File(userPath + ENTRY_FOLDER_NAME).mkdir();
            new File(userPath + BACKUP_FOLDER_NAME).mkdir();
            new File(userPath + CONFIG_FILE_NAME).createNewFile();

            bufferedWriter.write(user.getHashedPassword());

        } catch (IOException ignored) {
        }

        Database database = new Database(user.getUsername());
        database.saveUserSettings();
        return database;
    }

    public static List<String> getUserList() {
        return Arrays.stream(Objects.requireNonNull(new File(DATABASE_PATH).listFiles((dir, name) -> dir.isDirectory()))).map(File::getName).collect(Collectors.toList());
    }

    public static String getUserHashedPassword(String username) {
        try (var bufferedReader = new BufferedReader(new FileReader(new File(DATABASE_PATH + username + "/" + PASSWORD_FILE_NAME)))) {
            return bufferedReader.readLine();
        } catch (IOException ignored) {
        }

        return "";
    }

    // ------------------------- LOGGED IN USER -------------------------
    public ArrayList<Entry> getAllEntries(GuardedString guardedKey) {
        var entryList = new ArrayList<Entry>();
        var key = Security.accessGuardedKey(guardedKey);

        for (File file : Objects.requireNonNull(new File(userPath + ENTRY_FOLDER_NAME).listFiles())) {
            if (file.getName().endsWith(ENTRY_FILE_EXTENSION))

                try (ObjectInputStream out = new ObjectInputStream(Security.decryptStream(new FileInputStream(file.getPath()), key));) {
                    entryList.add((Entry) out.readObject());
                } catch (IOException | ClassNotFoundException | GeneralSecurityException ignored) {
                }
        }

        return entryList;
    }

    public void addEntry(Entry entry, GuardedString guardedKey) {
        var key = Security.accessGuardedKey(guardedKey);
        var date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        try (var out = new ObjectOutputStream(Security.encryptStream(new FileOutputStream(userPath + ENTRY_FOLDER_NAME + date + ENTRY_FILE_EXTENSION), key))) {
            out.writeObject(entry);
        } catch (IOException | GeneralSecurityException ignored) {
        }
    }

    // ------------------------- SETTINGS -------------------------
    public void loadUserSettings() {
        try (var bufferedReader = new BufferedReader(new FileReader(userPath + CONFIG_FILE_NAME))) {
            Settings.darkMode = bufferedReader.readLine().equals("true");
        } catch (IOException ignored) {
        }
    }

    public void saveUserSettings() {
        try (var bufferedWriter = new BufferedWriter(new FileWriter(new File(userPath + CONFIG_FILE_NAME)))) {
            bufferedWriter.write(Settings.darkMode ? "true" : "false");
        } catch (IOException ignored) {
        }
    }

}
