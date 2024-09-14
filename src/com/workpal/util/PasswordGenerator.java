package com.workpal.util;

import java.util.Random;

public class PasswordGenerator {

    public static String genererMotDePasseTemporaire() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder tempPassword = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            tempPassword.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return tempPassword.toString();
    }
}
