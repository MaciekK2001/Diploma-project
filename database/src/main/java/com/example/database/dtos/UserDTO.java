package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class UserDTO {
    UUID userId;

    String username;

    String firstName;

    String lastName;

    String email;

    String about;

    Integer matchesPlayed;

    Integer matchesWon;

    Instant joinedAt;
}
