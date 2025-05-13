package com.ts.juridico.infrastructure.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
