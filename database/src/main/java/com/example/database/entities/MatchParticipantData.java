package com.example.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MatchParticipantData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "match_participant_data")
public class MatchParticipantData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long matchParticipantDataId;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int timeOfRace;

    private double distanceMade;
}
