package com.medeirosgabriel.rpg.repository;

import com.medeirosgabriel.rpg.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CharacterRepository extends JpaRepository<Character, Long> {
}
