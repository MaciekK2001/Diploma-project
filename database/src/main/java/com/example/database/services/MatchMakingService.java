package com.example.database.services;

import com.example.database.config.WebSocketSessionManager;
import com.example.database.dtos.MatchErrorDTO;
import com.example.database.dtos.MatchStartDTO;
import com.example.database.entities.Match;
import com.example.database.entities.MatchParticipantData;
import com.example.database.entities.User;
import com.example.database.repositories.MatchParticipantDataRepository;
import com.example.database.repositories.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class MatchMakingService {

    private final WebSocketSessionManager webSocketSessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final MatchRepository matchRepository;
    private final MatchParticipantDataRepository matchParticipantDataRepository;

    @Scheduled(fixedRate = 500)
    private void makeMatch() {
        User firstPlayer = webSocketSessionManager.getFirstUser();
        User secondPlayer = webSocketSessionManager.getMatchingPlayer(firstPlayer);

        if(firstPlayer == null){
            return;
        }

        if(secondPlayer == null) {
            messagingTemplate.convertAndSendToUser(firstPlayer.getUsername(), "/queue/match",
                    MatchErrorDTO.builder()
                            .errorMessage("Couldn't find a worthy oponnent")
                            .build());
            return;
        }

        Instant matchStart = Instant.now().plusSeconds(15);

        MatchParticipantData firstMatchParticipantData = MatchParticipantData.builder()
                .user(firstPlayer)
                .timeOfRace(10).build();

        MatchParticipantData secondMatchParticipantData = MatchParticipantData.builder()
                .user(secondPlayer)
                .timeOfRace(10).build();

        Match match = Match.builder()
                .participants(List.of(firstMatchParticipantData, secondMatchParticipantData)).build();

        matchParticipantDataRepository.save(firstMatchParticipantData);
        matchParticipantDataRepository.save(secondMatchParticipantData);
        matchRepository.save(match);

        messagingTemplate.convertAndSendToUser(firstPlayer.getUsername(), "/match",
                MatchStartDTO.builder().username(secondPlayer.getUsername())
                        .matchStart(matchStart)
                        .matchId(match.getMatchId()).build());

        messagingTemplate.convertAndSendToUser(secondPlayer.getUsername(), "/match",
                MatchStartDTO.builder().username(firstPlayer.getUsername())
                        .matchStart(matchStart)
                        .matchId(match.getMatchId()).build());

        webSocketSessionManager.removeUsersInMatch(firstPlayer, secondPlayer);
    }

}

/*rozważyć sytuację w której użytkownik zamyka aplikację, opuszcza pojedynek, żeby nie nastackowały się wiadomości
na endpoincie osoby która wyszła z meczu
 */
