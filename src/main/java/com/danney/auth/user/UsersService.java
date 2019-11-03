package com.danney.auth.user;

import com.danney.auth.db.User;
import com.danney.auth.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Wello Horld!";
    }

    @PostMapping
    public String saveUser(@RequestBody User user) {
        userRepository.save(user);
        return "Saved User with id: " + user.getId();
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userRepository.deleteById(id);
        return "Record deleted";
    }
}
