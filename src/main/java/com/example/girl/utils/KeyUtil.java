package com.example.girl.utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String getKey() {
        Random random = new Random();
        int num = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(num);
    }
}
