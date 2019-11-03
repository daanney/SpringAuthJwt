package com.danney.auth.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;

    public Authentication asAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
