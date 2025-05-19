package com.matchnplay.service.auth;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import com.matchnplay.repository.UserRepository;
import com.matchnplay.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.matchnplay.exception.DuplicateResourceException;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(User user) {
        // Check username separately
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        // Check email separately
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save and return the user
        return userRepository.save(user);
    }
}
