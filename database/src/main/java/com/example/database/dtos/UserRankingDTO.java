package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserRankingDTO {
    AppUserBasicDataDTO appUserBasicDataDTO;
    Long burntCalories;
}
