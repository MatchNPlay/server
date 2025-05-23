package com.matchnplay.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record RegistrationRequestDto(
                @NotBlank @Size(min = 3, max = 20) @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters, digits, dots, underscores, and hyphens") String username,
                @Email @NotBlank String email,
                @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*._-]).+$", message = "Password must contain uppercase, lowercase, digit, and special character") String password,
                @NotBlank String fullname,
                @NotBlank String phone) {
}
