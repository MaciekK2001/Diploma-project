package com.example.database.config;

import com.example.database.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchMakingManager {

    private final WebSocketSessionManager webSocketSessionManager;

    @Scheduled(fixedRate = 500)
    private void makeMatch() {
        User firstPlayer = webSocketSessionManager.getFirstUser();
        User secondPlayer = webSocketSessionManager.getMatchingPlayer(firstPlayer);
    }
}
