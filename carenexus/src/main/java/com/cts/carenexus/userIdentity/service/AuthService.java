package com.cts.carenexus.userIdentity.service;

import com.cts.carenexus.userIdentity.dto.AuthResponse;
import com.cts.carenexus.userIdentity.dto.LoginRequest;
import com.cts.carenexus.userIdentity.dto.UserRegisterRequest;
import com.cts.carenexus.userIdentity.entities.User;

public interface AuthService {
    User registerUser(UserRegisterRequest request);
    AuthResponse login(LoginRequest request);
}