package io.syncscribe.common.utils;

import java.security.SecureRandom;

public class NanoIdGenerator {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ID_SIZE = 12;
    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        StringBuilder sb = new StringBuilder(ID_SIZE);
        for (int i = 0; i < ID_SIZE; i++) {
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }
}
