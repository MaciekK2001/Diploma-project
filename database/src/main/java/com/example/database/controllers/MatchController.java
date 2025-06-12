package com.example.database.controllers;

import com.example.database.dtos.webSocket.MatchProgress;
import com.example.database.dtos.webSocket.MatchRequest;
import com.example.database.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @MessageMapping("/send-match-request")
    public void sendMatchRequest(MatchRequest matchRequest) {
        matchService.sendMatchRequest(matchRequest);
    }

    @MessageMapping("/accept-match")
    public void acceptMatch(MatchRequest matchRequest) {
        matchService.acceptMatch(matchRequest);
    }

    @MessageMapping("/update-progress")
    public void updateProgress(String matchId, MatchProgress progress) {
        matchService.updateProgress(matchId, progress);
    }
}