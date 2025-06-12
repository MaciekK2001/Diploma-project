package com.example.database.config;

import com.example.database.services.JwtService;
import com.example.database.services.UserDetailsServiceImp;
import com.example.database.services.WaitingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableAsync
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;

    private final UserDetailsServiceImp userDetailsServiceImp;

    private final WaitingRoomService waitingRoomService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/waiting-room", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost/4200").withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String username = accessor.getFirstNativeHeader("Username");
//                    String token = accessor.getFirstNativeHeader("Authorization");
//                    String sessionId = accessor.getSessionId();
//
//                    if (username != null && token != null && token.startsWith("Bearer ")) {
//                        String jwtToken = token.substring(7);
//
//                        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);
//
//                        if (userDetails != null && userDetails.getUsername().equals(username)) {
//                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                                    userDetails, null, userDetails.getAuthorities()
//                            );
//                            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                            accessor.setUser(authentication);
//
//                            waitingRoomService.addUserToWaitingRoom(username, sessionId);
//                        } else {
//                            throw new IllegalArgumentException("Invalid token or username");
//                        }
//                    } else {
//                        throw new IllegalArgumentException("Missing username or token");
//                    }
//                }
//                return message;
//            }
//        });
//    }

}
