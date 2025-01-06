package com.example.database.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity(name = "Match")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID matchId;

    @CreationTimestamp
    private Instant createdAt;

    @OneToMany
    private List<MatchParticipantData> participants;

}
