package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.enums.UserRole;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/role/{role}")
    public List<User> getByRole(@PathVariable UserRole role) {
        return userService.getAllUsers()
                .stream()
                .filter(u -> u.getUserRole() == role)
                .toList();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existing = userService.getUser(id);
        existing.setUserName(user.getUserName());
        existing.setUserEmail(user.getUserEmail());
        existing.setUserRole(user.getUserRole());
        return userService.saveUser(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.getAllUsers().removeIf(u -> u.getUserId().equals(id));
    }
}

