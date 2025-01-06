package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class MatchStartDTO {
    String username;

    Instant matchStart;

    UUID matchId;
}
