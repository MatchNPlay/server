package com.matchnplay.service.user;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.matchnplay.exception.EmailVerificationException;
import com.matchnplay.repository.UserRepository;
import org.springframework.security.core.userdetails.User;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

        @Value("${email-verification.required}")
        private boolean emailVerificationRequired;

        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(final String username) {
                return userRepository.findByUsername(username).map(user -> {
                        if (emailVerificationRequired && !user.isEmailVerified()) {
                                throw new EmailVerificationException(
                                                "Your email is not verified");
                        }
                        return User.builder()
                                        .username(user.getUsername())
                                        .password(user.getPassword())
                                        .build();
                }).orElseThrow(() -> new UsernameNotFoundException(
                                "User with username [%s] not found".formatted(username)));
        }
}