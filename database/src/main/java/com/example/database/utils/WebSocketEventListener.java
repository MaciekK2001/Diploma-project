package com.example.database.utils;

import com.example.database.services.WaitingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

//    private final WaitingRoomService waitingRoomService;
//
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = headerAccessor.getUser().getName();
//        String sessionId = headerAccessor.getSessionId();
//
//        waitingRoomService.addUserToWaitingRoom(username, sessionId);
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        waitingRoomService.removeUserFromWaitingRoom(event.getSessionId());
//    }
}
