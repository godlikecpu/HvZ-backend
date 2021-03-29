package com.experis.hvzbackend.util;

import java.util.Random;

public class BiteCodeGenerator {

    // generate an 8 digit long bite code as a type String
    public static String generateBiteCode(){
        Random randomNumber = new Random();
        String numberString = "0123456789";
        int limit = 8;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            stringBuilder.append(numberString.charAt(randomNumber.nextInt(limit)));
        }
        return stringBuilder.toString();
    }
}
