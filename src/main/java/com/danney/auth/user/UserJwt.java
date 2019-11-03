package com.danney.auth.user;

import lombok.Getter;

@Getter
public class UserJwt {
    private String jwt;

    public UserJwt(String jwt) {
        this.jwt = jwt;
    }
}
