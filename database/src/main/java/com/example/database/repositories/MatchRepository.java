package com.example.database.repositories;

import com.example.database.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {
}
