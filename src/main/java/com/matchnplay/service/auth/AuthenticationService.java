package com.matchnplay.service.auth;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.matchnplay.dto.auth.AuthenticationRequestDto;
import com.matchnplay.dto.auth.AuthenticationResponseDto;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;

        public AuthenticationResponseDto authenticate(
                        final AuthenticationRequestDto request) {

                final var authToken = UsernamePasswordAuthenticationToken
                                .unauthenticated(request.username(), request.password());

                final var authentication = authenticationManager
                                .authenticate(authToken);

                final var token = jwtService.generateToken(request.username());
                return new AuthenticationResponseDto(token);
        }
}
