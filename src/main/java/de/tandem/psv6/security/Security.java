package de.tandem.psv6.security;

import org.apache.commons.codec.digest.DigestUtils;

public class Security {

    public static String hash(String string) {
        return DigestUtils.sha3_512Hex(string);
    }

    public static String encrypt(String string, String key) {
        return ""; //TODO
    }

    public static String decrypt(String string, String key) {
        return ""; //TODO
    }

}
