package com.matchnplay.controller.auth;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.matchnplay.service.auth.UserRegistrationService;
import com.matchnplay.service.auth.EmailVerificationService;
import com.matchnplay.mapper.UserRegistrationMapper;
import com.matchnplay.mapper.UserMapper;
import com.matchnplay.dto.auth.RegistrationRequestDto;
import com.matchnplay.dto.auth.RegistrationResponseDto;
import com.matchnplay.dto.user.UserProfileDto;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final UserRegistrationMapper userRegistrationMapper;
    private final EmailVerificationService emailVerificationService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var registeredUser = userRegistrationService.registerUser(
                userRegistrationMapper.toEntity(registrationDTO));

        boolean emailVerificationRequired = true;
        if (emailVerificationRequired) {
            emailVerificationService.sendVerificationToken(
                    registeredUser.getId(), registeredUser.getEmail());
        }
        return ResponseEntity.ok(
                userRegistrationMapper.toRegistrationResponseDto(registeredUser));
    }

    @GetMapping("/email/verify")
    public ResponseEntity<UserProfileDto> verifyEmail(
            @RequestParam("uid") String userId, @RequestParam("t") String token) {

        final var verifiedUser = emailVerificationService.verifyEmail(UUID.fromString(userId), token);

        return ResponseEntity.ok(userMapper.toUserProfileDto((com.matchnplay.model.User) verifiedUser));
    }

    @PostMapping("/email/resend-verification")
    public ResponseEntity<Void> resendVerificationLink(
            @RequestParam String email) {

        emailVerificationService.resendVerificationToken(email);
        return ResponseEntity.noContent().build();
    }
}