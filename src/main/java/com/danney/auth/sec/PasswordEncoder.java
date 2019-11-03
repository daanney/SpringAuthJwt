package com.danney.auth.sec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    private static PasswordEncoder instance;
    private BCryptPasswordEncoder encoder;

    private PasswordEncoder() {
        encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    public static BCryptPasswordEncoder get() {
        if(null == instance) instance = new PasswordEncoder();
        return instance.encoder;
    }
}
