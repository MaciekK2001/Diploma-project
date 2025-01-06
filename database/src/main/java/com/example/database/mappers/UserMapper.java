package com.example.database.mappers;

import com.example.database.dtos.UserDTO;
import com.example.database.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO mapUserToUserDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .about(user.getAbout())
                .firstName(user.getFirstName())
                .joinedAt(user.getJoinedAt())
                .lastName(user.getLastName())
                .matchesPlayed(user.getMatchesPlayed())
                .matchesWon(user.getMatchesWon())
                .username(user.getUsername()).build();
    }
}
