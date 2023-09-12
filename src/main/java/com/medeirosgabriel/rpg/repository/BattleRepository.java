package com.medeirosgabriel.rpg.repository;

import com.medeirosgabriel.rpg.model.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle, Long> {
}
