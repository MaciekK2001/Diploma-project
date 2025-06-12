package com.example.database.entities;

import com.example.database.dtos.webSocket.MatchResult;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class MatchSession {

    private String player1;
    private String player2;
    private Integer distance;
    private Timestamp matchStartTime;
    private Integer player1DistanceCovered;
    private Integer player2DistanceCovered;

    public MatchSession(String player1, String player2, Integer distance, Timestamp matchStartTime) {
        this.player1 = player1;
        this.player2 = player2;
        this.distance = distance;
        this.matchStartTime = matchStartTime;
        this.player1DistanceCovered = 0;
        this.player2DistanceCovered = 0;
    }

    public void updateProgress(String username, Integer distanceCovered) {
        if (player1.equals(username)) {
            player1DistanceCovered = distanceCovered;
        } else if (player2.equals(username)) {
            player2DistanceCovered = distanceCovered;
        }
    }

    public boolean isFinished() {
        return player1DistanceCovered >= distance || player2DistanceCovered >= distance;
    }

    public MatchResult getResult() {
        if (player1DistanceCovered >= distance && player2DistanceCovered >= distance) {
            return new MatchResult(player1, player2);
        } else if (player1DistanceCovered >= distance) {
            return new MatchResult(player1, player2);
        } else {
            return new MatchResult(player1, player2);
        }
    }

    public boolean isPlayerInMatch(String username) {
        return player1.equals(username) || player2.equals(username);
    }
}
