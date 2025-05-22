package com.matchnplay.dto.auth;

public record RegistrationResponseDto(
        String username,
        String email,
        Boolean emailVerificationRequired) {
}
