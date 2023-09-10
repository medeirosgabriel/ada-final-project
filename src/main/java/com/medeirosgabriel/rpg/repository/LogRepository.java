package com.medeirosgabriel.rpg.repository;

import com.medeirosgabriel.rpg.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
