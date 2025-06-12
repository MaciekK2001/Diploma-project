package com.example.database.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
@RequiredArgsConstructor
public class WaitingRoomService {

//    private final SimpMessagingTemplate messagingTemplate;
//    private final Map<String, String> onlineUsers = new ConcurrentHashMap<>();  // sessionId -> username
//
//    @Async
//    public void addUserToWaitingRoom(String username, String sessionId) {
//        onlineUsers.put(sessionId, username);
//        broadcastOnlineUsers();
//    }
//
//    @Async
//    public void removeUserFromWaitingRoom(String sessionId) {
//        onlineUsers.remove(sessionId);
//        broadcastOnlineUsers();
//    }
//
//    @Async
//    public List<String> getOnlineUsers() {
//        return new ArrayList<>(onlineUsers.values());
//    }
//
//    private void broadcastOnlineUsers() {
//        messagingTemplate.convertAndSend("/waiting-room/online-users", getOnlineUsers());
//    }
//
//    public String getUsernameBySessionId(String sessionId) {
//        return onlineUsers.get(sessionId);
//    }
}
