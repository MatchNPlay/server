package com.matchnplay.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDto(
                @NotBlank String username,
                @NotBlank String password) {
}