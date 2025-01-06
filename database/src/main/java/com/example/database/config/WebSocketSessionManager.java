package com.example.database.config;

import com.example.database.entities.User;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.stereotype.Component;

/*tutaj warto opisać dlaczego użyłem biHashMap
* dużo szybsze operacje dodawania i usuwania dla dużych danych, ale większy koszt pamięciowy
* powiedzieć dlaczego nie priority queue/linked list/hashLinkedList */


@Getter
@Component
public class WebSocketSessionManager {

    BiMap<String, User> userMap = Maps.synchronizedBiMap(HashBiMap.create());

    public void addConnectedUser(User user, String sessionId) {
        userMap.put(sessionId, user);
    }

    public void removeDisconnectedUser(String sessionId) {
        userMap.remove(sessionId);
    }

    public User getFirstUser() {
        String firstKey;
        try {
            firstKey = userMap.keySet().iterator().next();
        } catch (Exception e) {
            return null;
        }
        return userMap.get(firstKey);
    }

    public void removeUsersInMatch(User firstPlayer, User secondPlayer) {
        userMap.inverse().remove(firstPlayer);
        userMap.inverse().remove(secondPlayer);
    }
    //dorobić tutaj sytuację w której nie ma drugiego gracza i wtedy szukać dopasowania dla kolejnego w liście kolesia
    public User getMatchingPlayer(User firstPlayer) {
        for (User secondPlayer : userMap.values()) {
            if(Math.abs(secondPlayer.countWinRatio() - firstPlayer.countWinRatio()) <= 0.1){
                return secondPlayer;
            }
        }
        return null;
    }

}
