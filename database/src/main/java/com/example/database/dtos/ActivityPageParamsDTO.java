package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class ActivityPageParamsDTO {

    UUID userId;

    Integer firstActivity;

    Integer lastActivity;

    String sortBy;

    Sort.Direction sortOrder;

    List<String> conditions;
}
