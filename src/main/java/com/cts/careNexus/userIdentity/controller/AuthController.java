package com.cts.careNexus.userIdentity.controller;

import com.cts.careNexus.userIdentity.dto.AuthResponseDto;
import com.cts.careNexus.userIdentity.dto.LoginRequestDto;
import com.cts.careNexus.userIdentity.dto.UserRegisterRequestDto;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200") // <-- Added CrossOrigin for Angular
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterRequestDto request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}