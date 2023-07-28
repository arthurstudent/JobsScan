package com.jobsscan.utils;

import java.security.SecureRandom;
import java.util.Random;

public class GeneratorUtils {

    private static final Random secureRandom = new SecureRandom();
    private static final int BOUND = 10;

    public static String generateId() {
        return generateRandomString();
    }

    private static String generateRandomString() {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < BOUND; i++) {
            stringBuilder.append(secureRandom.nextInt(BOUND));
        }
        return stringBuilder.toString();
    }
}
