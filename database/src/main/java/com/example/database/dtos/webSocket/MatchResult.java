package com.example.database.dtos.webSocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchResult {
    private String winner;
    private String loser;
}