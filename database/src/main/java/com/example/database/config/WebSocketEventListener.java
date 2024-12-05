package com.example.database.config;

import com.example.database.entities.User;
import com.example.database.repositories.UserRepository;
import com.example.database.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketSessionManager webSocketSessionManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        User connectedUser = retriveCurrentLoggedInUser();
        String sessionId = headers.getSessionId();

        webSocketSessionManager.addConnectedUser(connectedUser, sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();

        webSocketSessionManager.removeDisconnectedUser(sessionId);
    }

    private User retriveCurrentLoggedInUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + " wasn't found")
        );
    }

}
