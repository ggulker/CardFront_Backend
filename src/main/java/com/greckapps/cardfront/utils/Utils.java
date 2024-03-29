package com.greckapps.cardfront.utils;

import java.util.Random;

public class Utils {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String RandomCode(int size){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            char randomChar = ALPHA_NUMERIC_STRING.charAt(index);
            builder.append(randomChar);
        }
        
        return builder.toString();
    }
}
