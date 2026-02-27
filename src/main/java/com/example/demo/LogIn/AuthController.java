package com.example.demo.LogIn;

import com.example.demo.LogIn.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.LogIn.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {

        return service.login(request);
    }
}

