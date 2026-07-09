package com.cts.carenexus.userIdentity.service;

import com.cts.carenexus.userIdentity.dto.AuthResponse;
import com.cts.carenexus.userIdentity.dto.LoginRequest;
import com.cts.carenexus.userIdentity.dto.UserRegisterRequest;
import com.cts.carenexus.userIdentity.entities.User;
import com.cts.carenexus.userIdentity.entities.UserStatus;
import com.cts.carenexus.userIdentity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public User registerUser(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setDepartmentId(request.getDepartmentId());
        user.setStatus(UserStatus.Active);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        if (user.getStatus() == UserStatus.Inactive) {
            throw new RuntimeException("Account is deactivated!");
        }

        String jwtToken = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                jwtToken,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}