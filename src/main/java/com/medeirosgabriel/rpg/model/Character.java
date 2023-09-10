package com.medeirosgabriel.rpg.model;

import com.medeirosgabriel.rpg.enums.CharacterType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private CharacterType characterType;
    private Long pv;
    private Long force;
    private Long defense;
    private Long agility;
    private Long diceQuantity;
    private Long diceFaces;

    public Character(String name, CharacterType characterType, Long pv, Long force, Long defense, Long agility, Long diceQuantity, Long diceFaces) {
        this.name = name;
        this.characterType = characterType;
        this.pv = pv;
        this.force = force;
        this.defense = defense;
        this.agility = agility;
        this.diceQuantity = diceQuantity;
        this.diceFaces = diceFaces;
    }

    public void takeDamage(long damage) {
        this.pv -= damage;
    }

    public boolean isAlive() {
        return this.pv > 0;
    }
}
