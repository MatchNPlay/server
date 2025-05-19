package com.matchnplay.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequestDto(
    @NotBlank String username,
    @Email @NotBlank String email,
    @NotBlank String password,
    @NotBlank String fullname,
    @NotBlank String phone
) { }
