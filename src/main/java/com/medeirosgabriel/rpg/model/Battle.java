package com.medeirosgabriel.rpg.model;

import com.medeirosgabriel.rpg.enums.NextStep;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
@Component
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    @JoinColumn(name = "character_id")
    private Character myCharacter;
    @OneToOne
    @JoinColumn(name = "enemy_id")
    private Character enemy;
    private NextStep nextStep;
}
