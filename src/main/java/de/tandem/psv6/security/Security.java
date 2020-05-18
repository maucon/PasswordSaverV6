package de.tandem.psv6.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class Security {

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String save_hash(String username, String string) {
        return DigestUtils.sha3_512Hex(username + string);
    }

    public static String key_hash(String string) {
        return DigestUtils.sha512_256Hex(string);
    }

    public static CipherOutputStream encryptStream(FileOutputStream fileOutputStream, String key) throws GeneralSecurityException {
        return new CipherOutputStream(fileOutputStream, initCipher(Cipher.ENCRYPT_MODE, key));
    }

    public static CipherInputStream decryptStream(FileInputStream fileInputStream, String key) throws GeneralSecurityException {
        return new CipherInputStream(fileInputStream, initCipher(Cipher.DECRYPT_MODE, key));
    }

    private static Cipher initCipher(int mode, String key) throws GeneralSecurityException {
        var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, new SecretKeySpec(Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 32), "AES"));
        return cipher;
    }

}
