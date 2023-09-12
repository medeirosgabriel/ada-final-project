package com.medeirosgabriel.rpg.dto;

import com.medeirosgabriel.rpg.enums.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCharacterDTO {
    private Long id;
    private String name;
    private CharacterType characterType;
    private Long pv;
    private Long force;
    private Long defense;
    private Long agility;
    private Long diceQuantity;
    private Long diceFaces;
}
