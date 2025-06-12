package com.example.database.services;

import com.example.database.dtos.webSocket.MatchStartResponse;
import com.example.database.entities.MatchSession;
import com.example.database.dtos.webSocket.MatchProgress;
import com.example.database.dtos.webSocket.MatchRequest;
import com.example.database.dtos.webSocket.MatchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final SimpMessagingTemplate messagingTemplate;

    private final Map<String, MatchSession> activeMatches = new ConcurrentHashMap<>();

    @Async
    public void sendMatchRequest(MatchRequest matchRequest) {
        messagingTemplate.convertAndSendToUser(
                matchRequest.getReceiver(),
                "/queue/match",
                matchRequest
        );
    }

    @Async
    public void acceptMatch(MatchRequest matchRequest) {
        if (isPlayerInActiveMatch(matchRequest.getReceiver()) || isPlayerInActiveMatch(matchRequest.getSender())) {
            return;
        }

        String matchId = generateMatchId(matchRequest.getSender(), matchRequest.getReceiver());
        Timestamp matchStartTime = new Timestamp(System.currentTimeMillis() + 30000);
        activeMatches.put(matchId, new MatchSession(matchRequest.getSender(), matchRequest.getReceiver(), matchRequest.getDistance(), matchStartTime));
        MatchStartResponse matchStartResponse = new MatchStartResponse(matchId, matchStartTime);
        messagingTemplate.convertAndSendToUser(
                matchRequest.getSender(),
                "/queue/match-start",
                matchStartResponse
        );
        messagingTemplate.convertAndSendToUser(
                matchRequest.getReceiver(),
                "/queue/match-start",
                matchStartResponse
        );
    }

    @Async
    public void updateProgress(String matchId, MatchProgress progress) {
        MatchSession session = activeMatches.get(matchId);
        if (session == null) return;

        session.updateProgress(progress.getUsername(), progress.getDistanceCovered());
        sendProgressToOtherUser(progress);
        if (session.isFinished()) {
            MatchResult result = session.getResult();
            messagingTemplate.convertAndSendToUser(session.getPlayer1(), "/queue/match-result", result);
            messagingTemplate.convertAndSendToUser(session.getPlayer2(), "/queue/match-result", result);
            activeMatches.remove(matchId);
            updateUsersMatchesStats(result);
        }
    }

    private String generateMatchId(String player1, String player2) {
        return player1 + "_" + player2 + "_" + System.currentTimeMillis();
    }

    private boolean isPlayerInActiveMatch(String username) {
        return activeMatches.values().stream()
                .anyMatch(session -> session.isPlayerInMatch(username));
    }

    private void sendProgressToOtherUser(MatchProgress progress) {

    }

    private void updateUsersMatchesStats(MatchResult matchResult) {

    }
}
