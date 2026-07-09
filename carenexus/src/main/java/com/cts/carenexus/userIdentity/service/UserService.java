package com.cts.carenexus.userIdentity.service;

import com.cts.carenexus.userIdentity.entities.User;
import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    User updateUserStatus(Long userId, String status);
}