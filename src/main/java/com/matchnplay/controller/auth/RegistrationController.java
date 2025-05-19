package com.matchnplay.controller.auth;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.matchnplay.service.auth.UserRegistrationService;
import com.matchnplay.mapper.UserRegistrationMapper;
import com.matchnplay.dto.auth.RegistrationRequestDto;
import com.matchnplay.dto.auth.RegistrationResponseDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final UserRegistrationMapper userRegistrationMapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var user = userRegistrationMapper.toEntity(registrationDTO);
        final var registeredUser = userRegistrationService.registerUser(user);

        return ResponseEntity.ok(
                userRegistrationMapper.toRegistrationResponseDto(registeredUser));
    }
}