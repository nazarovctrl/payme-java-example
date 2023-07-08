package com.example.paymejava.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AuthUtil {
    @Value("${paycom.user.name}")
    private String username;

    @Value("${paycom.user.password}")
    private String password;

    public boolean isUnauthorized(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return true;
        }
        String tokenBase64 = authHeader.substring(6);
        String token = new String(Base64.getDecoder().decode(tokenBase64), StandardCharsets.UTF_8);
        String[] auth = token.split(":");
        return !username.equals(auth[0]) || !password.equals(auth[1]);
    }
}
