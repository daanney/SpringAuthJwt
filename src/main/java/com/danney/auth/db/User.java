package com.danney.auth.db;

import com.danney.auth.sec.PasswordEncoder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@ToString
@Document(collection="users")
public class User implements UserDetails {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;

    private String password;
    private boolean accountExpired = false;
    private boolean accountLocked = false;
    private boolean credentialsExpired = false;
    private boolean enabled = true;
    private Collection<? extends GrantedAuthority> authorities;

    public boolean isMissingFields() {
        return username == null || username.isEmpty() ||
                password == null || password.isEmpty();
    }

    public User forCreate() {
        hashPassword();
        id = null;
        return this;
    }

    public void hashPassword() {
        if(null == password || password.isEmpty()) return;
        password = PasswordEncoder.get().encode(password);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
}
