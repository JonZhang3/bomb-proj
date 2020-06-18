package com.bombproj.utils;

import java.util.Random;
import java.util.regex.Pattern;

public final class Utils {

    private Utils() {
    }

    private static final String CHARS = "123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ789abcd";
    private static final Pattern PATTERN_EMAIL =
        Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern PATTERN_PHONE = Pattern.compile("1[3456789]\\d{9}");

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
        for (int i = 0; i < length; i++) {
            result.append(CHARS.charAt(random.nextInt(size)));
        }
        return result.toString();
    }

    public static boolean checkEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return PATTERN_EMAIL.matcher(email).matches();
    }

    public static boolean checkPhone(String phone) {
        if(isEmpty(phone)) {
            return false;
        }
        return PATTERN_PHONE.matcher(phone).matches();
    }

    public static String getFileSuffix(String fileName) {
        if(isEmpty(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
