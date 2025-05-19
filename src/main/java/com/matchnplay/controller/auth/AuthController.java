package com.matchnplay.controller.auth;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import com.matchnplay.service.auth.AuthenticationService;
import com.matchnplay.dto.auth.AuthenticationRequestDto;
import com.matchnplay.dto.auth.AuthenticationResponseDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
      @RequestBody final AuthenticationRequestDto authenticationRequestDto
    ) {
        return ResponseEntity.ok(
          authenticationService.authenticate(authenticationRequestDto));
    }
}
