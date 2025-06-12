package com.example.database.dtos.webSocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class MatchStartResponse {
    private String matchId;
    private Timestamp startTime;
}
