package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.dto.AuthResponseDto;
import com.cts.careNexus.userIdentity.dto.LoginRequestDto;
import com.cts.careNexus.userIdentity.dto.UserRegisterRequestDto;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.entities.UserStatus;
import com.cts.careNexus.userIdentity.repository.UserRepo;
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

    // Checks for duplicate email, encodes the password, sets active status, and persists the new user entity.
    @Override
    @Transactional
    public User registerUser(UserRegisterRequestDto request) {
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

    // Authenticates credentials, verifies the user account is active, and issues a JWT token in the response DTO.
    @Override
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        if (user.getStatus() == UserStatus.Inactive) {
            throw new RuntimeException("Account is deactivated!");
        }

        String jwtToken = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponseDto(
                jwtToken,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}