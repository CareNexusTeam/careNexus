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

    // Retrieves a specific user record by their unique ID or throws an exception if not found.
    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    // Fetches and returns a complete list of all user entities registered in the database.
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Finds the user, validates and converts the status string to an enum, then persists the updated record.
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