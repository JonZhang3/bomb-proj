package com.bombproj.utils;

import com.queryflow.key.KeyGenerateUtil;

import java.util.UUID;

public final class ShortIdUtil {

    private ShortIdUtil() {

    }

    private static final long INIT_TIME = 1460332800000L;

    private static final char[] SYMBOLS = {
        '0','1','2','3','4','5','6','7','8','9',
        'a','b','c','d','e','f','g','h','i','j',
        'k','l','m','n','o','p','q','r','s','t',
        'u','v','w','x','y','z','A','B','C','D',
        'E','F','G','H','I','J','K','L','M','N',
        'O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    private static final int DEFAULT_INTERVAL = 1;

    private static final int DEFAULT_SALT_LENGTH = 2;

    public static String gen() {
        long now = System.currentTimeMillis();
        long intervalTime = (long) Math.floor(now - INIT_TIME);

        return "";
    }

    private static String genSalt(int len) {
        StringBuilder result = new StringBuilder(len);
        for(int i = 0; i < len; i++) {

        }
        return "";
    }

    private static String paddingLeft() {
        return "";
    }

    public static void main(String[] args) {
        System.out.println("000123".substring(-3));
    }

}
