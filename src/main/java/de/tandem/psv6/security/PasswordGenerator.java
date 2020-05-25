package de.tandem.psv6.security;

import de.tandem.psv6.entity.Settings;

import java.security.SecureRandom;

public class PasswordGenerator {

    // BANNED CHARS: °´§²³€µäÄüÜöÖ (and space)
    // ALLOWED SYMBOLS: ^!"$%&/()=?`+*#'-_.:,;<>|~\@[]{}
    private static final char[] LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] NUMBERS = "1234567890".toCharArray();
    private static final char[] SYMBOLS = "^!\"$%&/()=?`+*#'-_.:,;<>|~\\@[]{}".toCharArray();
    
    public static String create() {
        int array_length = LOWERCASE_LETTERS.length;
        if (Settings.useUppercaseLetters) array_length += UPPERCASE_LETTERS.length;
        if (Settings.useNumbers) array_length += NUMBERS.length;
        if (Settings.useSymbols) array_length += SYMBOLS.length;

        var character = new char[array_length];
        var index = 0;

        for (char c : LOWERCASE_LETTERS)
            character[index++] = c;
        if (Settings.useUppercaseLetters) for (char c : UPPERCASE_LETTERS)
            character[index++] = c;
        if (Settings.useNumbers) for (char c : NUMBERS)
            character[index++] = c;
        if (Settings.useSymbols) for (char c : SYMBOLS)
            character[index++] = c;

        var random = new SecureRandom();
        var stringBuilder = new StringBuilder();
        for (var i = 0; i < Settings.passwordLength; i++)
            stringBuilder.append(character[(int) (random.nextFloat() * character.length)]);
        return stringBuilder.toString();
    }

}
