package com.matchnplay.mapper;

import org.springframework.stereotype.Component;
import com.matchnplay.dto.user.UserProfileDto;
import com.matchnplay.model.User;

@Component
public class UserMapper {
    public UserProfileDto toUserProfileDto(final User user) {
        return new UserProfileDto(user.getEmail(), user.getUsername(), user.getFullname(), user.getPhone());
    }
}
