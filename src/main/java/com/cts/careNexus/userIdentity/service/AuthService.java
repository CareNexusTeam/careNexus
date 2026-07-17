package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.dto.AuthResponseDto;
import com.cts.careNexus.userIdentity.dto.LoginRequestDto;
import com.cts.careNexus.userIdentity.dto.UserRegisterRequestDto;
import com.cts.careNexus.userIdentity.entities.User;

public interface AuthService {
    User registerUser(UserRegisterRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
}