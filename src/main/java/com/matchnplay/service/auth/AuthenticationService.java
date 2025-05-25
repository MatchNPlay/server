package com.matchnplay.service.auth;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.matchnplay.dto.auth.AuthenticationRequestDto;
import com.matchnplay.dto.auth.AuthenticationResponseDto;
import com.matchnplay.model.User;
import com.matchnplay.model.RefreshToken;
import com.matchnplay.repository.UserRepository;
import com.matchnplay.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final RefreshTokenRepository refreshTokenRepository;
        private final long refreshTokenTtl = 86400000L;

        public AuthenticationResponseDto authenticate(
                        final AuthenticationRequestDto request) {

                // Authenticate the user
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.username(),
                                                request.password()));

                // Generate JWT access token
                String accessToken = jwtService.generateToken(request.username());

                // Fetch user and create refresh token
                User user = userRepository.findByUsername(request.username())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setUser(user);
                refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenTtl));
                refreshTokenRepository.save(refreshToken);

                return new AuthenticationResponseDto(accessToken, refreshToken.getId());
        }

        public AuthenticationResponseDto refreshToken(UUID refreshToken) {
                final RefreshToken refreshTokenEntity = refreshTokenRepository
                                .findById(refreshToken)
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST, "Invalid or expired refresh token"));

                String newAccessToken = jwtService.generateToken(refreshTokenEntity.getUser().getUsername());

                return new AuthenticationResponseDto(newAccessToken, refreshTokenEntity.getId());
        }

        public void revokeRefreshToken(UUID refreshToken) {
                refreshTokenRepository.deleteById(refreshToken);
        }
}