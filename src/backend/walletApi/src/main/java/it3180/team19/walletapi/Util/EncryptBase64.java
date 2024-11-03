package it3180.team19.walletapi.Util;

import java.util.Base64;

public class EncryptBase64 {
    public static String encrypt(String plainText) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(plainText.getBytes());
    }
}
