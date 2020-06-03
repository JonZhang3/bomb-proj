package com.bombproj.utils;

import java.util.Random;

public final class Utils {

    private Utils() {
    }

    private static final String CHARS = "123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ789abcd";

    public static boolean isEmpty(String src) {
        return src == null || src.isEmpty();
    }

    public static boolean isNotEmpty(String src) {
        return !isEmpty(src);
    }

    public static String getChars(int length) {
        int size = CHARS.length();
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            result.append(CHARS.charAt(random.nextInt(size)));
        }
        return result.toString();
    }

}
