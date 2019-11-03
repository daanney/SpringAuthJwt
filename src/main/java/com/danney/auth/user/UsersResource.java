package com.danney.auth.user;

import com.danney.auth.db.User;
import com.danney.auth.sec.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersResource {

    @Autowired
    private UsersService service;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello() {
        return "Wello Horld!";
    }

    @GetMapping
    public List<User> get() {
        return service.find();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return service.find(id);
    }

    @PostMapping("/login")
    public UserJwt login(@RequestBody LoginRequest loginRequest) {
        try {
            authManager.authenticate(loginRequest.asAuthentication());
        }catch(BadCredentialsException e) {
            throw new IllegalArgumentException("try again");
        }

        return jwtUtil.generate(service.findByUsername(loginRequest.getUsername()));
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        if(!service.save(user)) {
            throw new IllegalArgumentException(service.getValidationError());
        }

        user.setPassword(null);
        return user;
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable String id) {
        return service.delete(id);
    }
}
