package com.example.database.repositories;

import com.example.database.entities.MatchParticipantData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchParticipantDataRepository extends JpaRepository<MatchParticipantData, Long> {
}
