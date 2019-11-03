package com.danney.auth.user;

import com.danney.auth.db.User;
import com.danney.auth.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsersService implements UserDetailsService {

    private String validationError;

    @Autowired
    private UserRepository userRepository;

    public List<User> find() {
        return userRepository.findAllQuery();
    }

    public User find(String id) {
        return userRepository.findByIdQuery(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    protected boolean save(User user) {
        return this.validate(user) && !userRepository.save(user.forCreate()).getId().isEmpty();
    }

    protected User delete(String id) {
        User user = find(id); // exception is thrown if not found
        userRepository.deleteById(id);
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    public boolean validate(User user) {
        validationError = "";

        if(user.isMissingFields()) {
            validationError = "Mandatory fields are not present";
            return false;
        }

        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            validationError = "Username is already taken";
            return false;
        }

        return true;
    }

    public String getValidationError() {
        return validationError;
    }
}
