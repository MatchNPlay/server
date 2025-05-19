package com.matchnplay.dto.user;

public record UserProfileDto(
    String email, 
    String username,
    String fullname,
    String phone
) {
}