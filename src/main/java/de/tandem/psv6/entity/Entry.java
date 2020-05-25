package de.tandem.psv6.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Entry implements Serializable {

    // BANNED CHARS: ´§²³€µäÄüÜöÖ
    private static final long serialVersionUID = 1L;
    private static final transient String ALLOWED_CHARS = " ^!\"$%&/()=?`+*#'-_.:,;<>|~\\@[]{}abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private String name;
    private String login;
    private String password;
    private String description;
    private transient String fileName;

    public static boolean isAllowedString(String string) {
        for (char c : string.toCharArray()) if (!ALLOWED_CHARS.contains(c + ""))
                return false;
        return true;
    }

}
