package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.dto.AuthResponse;
import com.cts.careNexus.userIdentity.dto.LoginRequest;
import com.cts.careNexus.userIdentity.dto.UserRegisterRequest;
import com.cts.careNexus.userIdentity.entities.User;

public interface AuthService {
    User registerUser(UserRegisterRequest request);
    AuthResponse login(LoginRequest request);
}