package de.tandem.psv6.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;

public class Security {

    public static String hash(String string) {
        return DigestUtils.sha3_512Hex(string);
    }

    public static String encrypt(String text, String key) throws GeneralSecurityException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        return new String(cipher.doFinal(text.getBytes()));
    }

    public static String decrypt(String text, String key) throws GeneralSecurityException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        return new String(cipher.doFinal(text.getBytes()));
    }

}
