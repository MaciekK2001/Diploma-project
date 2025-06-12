package com.example.database.dtos.webSocket;

import lombok.Data;

@Data
public class MatchProgress {
    private String username;
    private Integer distanceCovered;
    private String currentTime;
}