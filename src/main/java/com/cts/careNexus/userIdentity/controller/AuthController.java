package com.cts.careNexus.userIdentity.controller;

import com.cts.careNexus.userIdentity.dto.AuthResponse;
import com.cts.careNexus.userIdentity.dto.LoginRequest;
import com.cts.careNexus.userIdentity.dto.UserRegisterRequest;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}