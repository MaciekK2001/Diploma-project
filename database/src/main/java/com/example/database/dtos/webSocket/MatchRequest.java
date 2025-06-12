package com.example.database.dtos.webSocket;

import lombok.Data;

@Data
public class MatchRequest {
    private String sender;
    private String receiver;
    private Integer distance;
}