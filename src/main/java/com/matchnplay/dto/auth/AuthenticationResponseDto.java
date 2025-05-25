package com.matchnplay.dto.auth;

import java.util.UUID;

public record AuthenticationResponseDto(
        String accessToken,
        UUID refreshToken) {
}