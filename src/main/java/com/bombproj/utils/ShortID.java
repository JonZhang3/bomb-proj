package com.bombproj.utils;

import java.security.SecureRandom;
import java.util.Random;

public final class ShortID {

    public static final String DEFAULT_ALPHABET =
        "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZab";

    private static final long DEFAULT_REDUCE_TIME = 1403265799803L;

    private static final int DEFAULT_VERSION = 6;

    private static final ShortID INSTANCE = new ShortID(new SecureRandom(), DEFAULT_ALPHABET, DEFAULT_REDUCE_TIME,
        DEFAULT_VERSION, 0);

    public static String randomID() {
        return INSTANCE.generate();
    }

    private final Random random;

    private final long reduceTime;

    private final int version;

    private final int clusterWorkerId;

    private volatile int counter;

    private volatile long previousSeconds;

    private final char[] shuffled;

    private ShortID(Random random, String alphabet, long reduceTime, int version, int clusterWorkerId) {
        this.random = random;
        this.shuffled = shuffle(alphabet);
        this.reduceTime = reduceTime;
        this.version = version;
        this.clusterWorkerId = clusterWorkerId;
    }

    public String generate() {
        String str = "";

        long seconds = (long) Math.floor((System.currentTimeMillis() - reduceTime) * 0.001);

        if (seconds == previousSeconds) {
            counter++;
        } else {
            counter = 0;
            previousSeconds = seconds;
        }

        str = str + encode(version);
        str = str + encode(clusterWorkerId);

        if (counter > 0) {
            str = str + encode(counter);
        }

        str = str + encode((int) seconds);

        return str;
    }

    private String encode(int number) {
        int loopCounter = 0;
        boolean done = false;

        StringBuilder str = new StringBuilder();

        int index;
        while (!done) {
            index = ((number >> (4 * loopCounter)) & 0x0f) | randomByte();
            str.append(shuffled[index]);
            done = number < (Math.pow(16, loopCounter + 1));
            loopCounter++;
        }
        return str.toString();
    }

    private int randomByte() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0] & 0x30;
    }

    private char[] shuffle(String alphabet) {
        StringBuilder source = new StringBuilder(alphabet);
        StringBuilder target = new StringBuilder(source.length());

        double r;
        int charIndex;

        while (source.length() > 0) {
            r = random.nextDouble();
            charIndex = (int) Math.floor(r * source.length());

            target.append(source.charAt(charIndex));
            source.deleteCharAt(charIndex);
        }

        return target.toString().toCharArray();
    }

}
