package com.medeirosgabriel.rpg.repository;

import com.medeirosgabriel.rpg.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
