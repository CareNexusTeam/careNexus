package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.entities.UserStatus;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUserStatus(Long userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        try {
            user.setStatus(UserStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Status! Use Active or Inactive.");
        }

        return userRepository.save(user);
    }
}