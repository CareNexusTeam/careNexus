package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.entities.User;
import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    User updateUserStatus(Long userId, String status);
}