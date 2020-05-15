package de.tandem.psv6.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

public class Security {

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String save_hash(String string) {
        return DigestUtils.sha3_512Hex(string);
    }

    public static String key_hash(String string) {
        return DigestUtils.sha512_256Hex(string);
    }

    public static String encrypt(String text, String key) throws GeneralSecurityException {
        return bytesToHex(Base64.getEncoder().encode(initCipher(Cipher.ENCRYPT_MODE, key).doFinal(text.getBytes(StandardCharsets.UTF_8))));
    }

    public static String decrypt(String text, String key) throws GeneralSecurityException {
        return new String(initCipher(Cipher.DECRYPT_MODE, key).doFinal(Base64.getDecoder().decode(hexToBytes(text))));
    }

    private static Cipher initCipher(int mode, String key) throws GeneralSecurityException {
        SecretKeySpec secretKey = new SecretKeySpec(Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 32), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, secretKey);

        return cipher;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;

            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hexChars);
    }

    private static byte[] hexToBytes(String hexChars) {
        int len = hexChars.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2)
            data[i / 2] = (byte) ((Character.digit(hexChars.charAt(i), 16) << 4) + Character.digit(hexChars.charAt(i + 1), 16));

        return data;
    }

}
