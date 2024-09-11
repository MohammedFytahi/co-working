package com.workpal.util;

import java.util.regex.Pattern;

public class InputValidator {

    // Validate name (non-empty and contains only letters)
    public static boolean validateName(String name) {
        return name != null && name.matches("[a-zA-Z\\s]+");
    }

    // Validate email format
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Validate password (minimum 8 characters)
    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 8;
    }

    // Validate phone number (only digits, length 10)
    public static boolean validatePhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Validate address (non-empty)
    public static boolean validateAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }
}
