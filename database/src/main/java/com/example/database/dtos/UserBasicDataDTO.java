package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class UserBasicDataDTO {
    UUID userId;

    String username;
}
