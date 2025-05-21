package com.matchnplay.mapper;

import org.springframework.stereotype.Component;
import com.matchnplay.dto.auth.RegistrationRequestDto;
import com.matchnplay.dto.auth.RegistrationResponseDto;
import com.matchnplay.model.User;

@Component
public class UserRegistrationMapper {

    public User toEntity(RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setEmail(registrationRequestDto.email());
        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());
        user.setFullname(registrationRequestDto.fullname());
        user.setPhone(registrationRequestDto.phone());
        user.setEmail_verified(false);

        return user;
    }

    public RegistrationResponseDto toRegistrationResponseDto(
            final User user) {

        return new RegistrationResponseDto(
                user.getUsername(), user.getEmail());
    }

}