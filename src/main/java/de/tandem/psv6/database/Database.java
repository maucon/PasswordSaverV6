package de.tandem.psv6.database;

import de.tandem.psv6.entity.User;
import de.tandem.psv6.exceptions.UserAlreadyExistsException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Database {

    private static final String DATABASE_PATH = "database/";
    private static final String PASSWORD_FILE_NAME = "user.key";
    private static final String ENTRY_FOLDER_NAME = "entries/";

    private final String userPath;

    public Database(String username) {
        this.userPath = DATABASE_PATH + username + "/";
    }

    public static void setupUserFolder(User user) {
        var userPath = DATABASE_PATH + user.getUsername() + "/";

        if (!new File(userPath).mkdir())
            throw new UserAlreadyExistsException();

        new File(userPath + ENTRY_FOLDER_NAME).mkdir();

        try (var bufferedWriter = new BufferedWriter(new FileWriter(new File(userPath + PASSWORD_FILE_NAME)))) {
            bufferedWriter.write(user.getHashedPassword());
        } catch (IOException ignored) {
        }
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

}
