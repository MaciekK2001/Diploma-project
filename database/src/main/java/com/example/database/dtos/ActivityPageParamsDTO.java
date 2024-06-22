package com.example.database.dtos;

import com.example.database.entities.ActivityType;
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

    Integer pageSize;

    Integer pageNumber;

    String sortBy;

    Sort.Direction sortOrder;

    List<ActivityType> conditions;
}
