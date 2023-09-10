package com.medeirosgabriel.rpg.dto;

import com.medeirosgabriel.rpg.enums.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterDTO {
    private String name;
    private CharacterType type;
}
