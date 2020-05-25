package de.tandem.psv6.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private static final transient String ALLOWED_CHARS = "-_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private String username;
    private String hashedPassword;

    public static boolean isAllowedUsername(String string) {
        for (char c : string.toCharArray())
            if (!ALLOWED_CHARS.contains(c + ""))
                return false;
        return true;
    }

}
