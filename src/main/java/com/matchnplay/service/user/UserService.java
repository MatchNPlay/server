package com.matchnplay.service.user;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.matchnplay.repository.UserRepository;
import com.matchnplay.model.User;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.GONE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(GONE,
                        "The user account has been deleted or inactivated"));
    }
}