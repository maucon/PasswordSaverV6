package de.tandem.psv6.database;

import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.Setting;
import de.tandem.psv6.entity.User;
import de.tandem.psv6.exceptions.UserAlreadyExistsException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public static Database setupUserFolder(User user) {
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

        return new Database(userPath);
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

    public ArrayList<Entry> getAllEntries() {
        var entryList = new ArrayList<Entry>();

        for (File file : Objects.requireNonNull(new File(userPath + entryList).listFiles())) {
            if (file.getName().endsWith(ENTRY_FILE_EXTENSION))

                try (var bufferedReader = new BufferedReader(new FileReader(file))) {
                    entryList.add(new Entry(bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine()));
                } catch (IOException ignored) {
                }
        }

        return entryList;
    }

    public void loadUserSettings() {
        try (var bufferedReader = new BufferedReader(new FileReader(userPath + CONFIG_FILE_NAME))) {
            Setting.darkMode = bufferedReader.readLine().equals("true");
        } catch (IOException ignored) {
        }
    }

    public void saveUserSettings() {
        try (var bufferedWriter = new BufferedWriter(new FileWriter(new File(userPath + CONFIG_FILE_NAME)))) {
            bufferedWriter.write(Setting.darkMode ? "true" : "false");
        } catch (IOException ignored) {
        }
    }
}
