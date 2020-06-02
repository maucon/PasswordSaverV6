package de.tandem.psv6.database;

import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.Settings;
import de.tandem.psv6.entity.User;
import de.tandem.psv6.exceptions.FileModificationException;
import de.tandem.psv6.exceptions.NotLoggedInException;
import de.tandem.psv6.exceptions.UserAlreadyExistsException;
import de.tandem.psv6.security.Security;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Database {

    public static final String ENTRY_FILE_EXTENSION = ".entry";
    public static final String ENTRY_FILE_NAME_FORMAT = "yyyyMMddHHmmssSSS";
    private static final String DATABASE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().toString() + "/PasswordSaverV6/database/";
    private static final String PASSWORD_FILE_NAME = "user.key";
    private static final String ENTRY_FOLDER_NAME = "entries/";
    private static final String CONFIG_FILE_NAME = "config.cfg";
    private static Database instance;
    private final String userPath;

    private Database(String username) {
        this.userPath = DATABASE_PATH + username + "/";
    }

    // ------------------------- SINGLETON -------------------------
    public static Database getInstance() {
        if (instance == null) throw new NotLoggedInException();
        return instance;
    }

    public static Database createInstance(String username) {
        return instance = new Database(username);
    }

// --Commented out by Inspection START (25.05.2020 16:48):
//    public static void removeInstance() {
//        instance = null;
//    }
// --Commented out by Inspection STOP (25.05.2020 16:48)

    // ------------------------- STATIC -------------------------
    public static void setupUser(User user) {
        String userPath = DATABASE_PATH + user.getUsername() + "/";

        if (!new File(userPath).mkdir())
            throw new UserAlreadyExistsException();

        try (var bufferedWriter = new BufferedWriter(new FileWriter(new File(userPath + PASSWORD_FILE_NAME)))) {

            if (!new File(userPath + ENTRY_FOLDER_NAME).mkdir())
                throw new FileModificationException("Couldn't create folder");

            bufferedWriter.write(user.getHashedPassword());

        } catch (IOException ignored) {
        }

        new Database(user.getUsername()).saveUserSettings();
    }

    public static List<String> getUserList() {
        return Arrays.stream(Objects.requireNonNull(new File(DATABASE_PATH).listFiles((dir, name) -> dir.isDirectory()))).map(File::getName).collect(Collectors.toList());
    }

    public static String getUserHashedPassword(String username) {
        try (var bufferedReader = new BufferedReader(new FileReader(new File(DATABASE_PATH + username + "/" + PASSWORD_FILE_NAME)))) {
            return bufferedReader.readLine();
        } catch (IOException ignored) {
            throw new FileModificationException("Can't read users hashed password: " + username);
        }
    }

    // ------------------------- LOGGED IN USER -------------------------
    public List<Entry> getAllEntries() {
        return Arrays.stream(Objects.requireNonNull(new File(userPath + ENTRY_FOLDER_NAME).listFiles()))
                .filter(file -> file.getName().endsWith(ENTRY_FILE_EXTENSION))
                .map(file -> {
                    try (var out = new ObjectInputStream(Security.decryptStream(new FileInputStream(file.getPath()), Security.accessGuardedKey(Security.guardedString)))) {

                        var entry = (Entry) out.readObject();
                        entry.setFileName(file.getName());
                        return entry;

                    } catch (IOException | ClassNotFoundException | GeneralSecurityException ignored) {
                    }

                    throw new FileModificationException("Can't read entry: " + file.getName());
                })
                .collect(Collectors.toList());
    }

    public Entry addEntry(Entry entry) {
        var filename = new SimpleDateFormat(ENTRY_FILE_NAME_FORMAT).format(new Date()) + ENTRY_FILE_EXTENSION;
        try (var out = new ObjectOutputStream(Security.encryptStream(new FileOutputStream(
                userPath + ENTRY_FOLDER_NAME + filename), Security.accessGuardedKey(Security.guardedString)))) {
            entry.setFileName(filename);
            out.writeObject(entry);
            return entry;
        } catch (IOException | GeneralSecurityException ignored) {
            throw new FileModificationException("Couldn't create Entry: " + entry);
        }
    }

    public void removeEntry(Entry entry) {
        if (!new File(userPath + ENTRY_FOLDER_NAME + entry.getFileName()).delete())
            throw new FileModificationException("Couldn't delete entry: " + entry);
    }

    public void editEntry(Entry entry) {
        addEntry(entry);
        removeEntry(entry);
    }

    // ------------------------- SETTINGS -------------------------
    public void loadUserSettings() {
        try (var bufferedReader = new BufferedReader(new FileReader(userPath + CONFIG_FILE_NAME))) {
            Settings.darkMode = bufferedReader.readLine().equals("true");
            Settings.passwordLength = Integer.parseInt(bufferedReader.readLine());
            Settings.useUppercaseLetters = bufferedReader.readLine().equals("true");
            Settings.useNumbers = bufferedReader.readLine().equals("true");
            Settings.useSymbols = bufferedReader.readLine().equals("true");
        } catch (IOException ignored) {
            throw new FileModificationException("Couldn't read user settings: " + userPath);
        }
    }

    public void saveUserSettings() {
        try (var bufferedWriter = new BufferedWriter(new FileWriter(new File(userPath + CONFIG_FILE_NAME)))) {
            bufferedWriter.write(Settings.darkMode ? "true" : "false");
            bufferedWriter.newLine();
            bufferedWriter.write("" + Settings.passwordLength);
            bufferedWriter.newLine();
            bufferedWriter.write(Settings.useUppercaseLetters ? "true" : "false");
            bufferedWriter.newLine();
            bufferedWriter.write(Settings.useNumbers ? "true" : "false");
            bufferedWriter.newLine();
            bufferedWriter.write(Settings.useSymbols ? "true" : "false");
        } catch (IOException ignored) {
            throw new FileModificationException("Couldn't save user settings: " + userPath);
        }
    }

}
