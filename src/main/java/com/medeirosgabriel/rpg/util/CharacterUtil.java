package com.medeirosgabriel.rpg.util;

import com.medeirosgabriel.rpg.dto.CharacterDTO;
import com.medeirosgabriel.rpg.dto.UpdateCharacterDTO;
import com.medeirosgabriel.rpg.enums.CharacterType;
import com.medeirosgabriel.rpg.exceptions.CharacterTypeNotFoundException;
import com.medeirosgabriel.rpg.model.Character;

import java.util.Random;
import java.util.UUID;

public class CharacterUtil {

    public static Character createCharacter(CharacterDTO addCharacterDTO) throws CharacterTypeNotFoundException {

        Character.CharacterBuilder characterBuilder = Character.builder();
        characterBuilder.name(addCharacterDTO.getName());
        CharacterType type = addCharacterDTO.getType();

        if (type.equals(CharacterType.WARRIOR)) {
            characterBuilder.characterType(type);
            characterBuilder.pv(20L);
            characterBuilder.force(7L);
            characterBuilder.defense(5L);
            characterBuilder.agility(6L);
            characterBuilder.diceQuantity(1L);
            characterBuilder.diceFaces(12L);
        } else if (type.equals(CharacterType.BARBARIAN)) {
            characterBuilder.characterType(type);
            characterBuilder.pv(21L);
            characterBuilder.force(10L);
            characterBuilder.defense(2L);
            characterBuilder.agility(5L);
            characterBuilder.diceQuantity(2L);
            characterBuilder.diceFaces(8L);
        } else if (type.equals(CharacterType.KNIGHT)) {
            characterBuilder.characterType(type);
            characterBuilder.pv(26L);
            characterBuilder.force(6L);
            characterBuilder.defense(8L);
            characterBuilder.agility(3L);
            characterBuilder.diceQuantity(2L);
            characterBuilder.diceFaces(6L);
        } else if (type.equals(CharacterType.ORC)) {
            characterBuilder.characterType(type);
            characterBuilder.pv(42L);
            characterBuilder.force(7L);
            characterBuilder.defense(1L);
            characterBuilder.agility(2L);
            characterBuilder.diceQuantity(3L);
            characterBuilder.diceFaces(4L);
        } else if (type.equals(CharacterType.GIANT)) {
            characterBuilder.characterType(type);
            characterBuilder.pv(34L);
            characterBuilder.force(10L);
            characterBuilder.defense(4L);
            characterBuilder.agility(4L);
            characterBuilder.diceQuantity(2L);
            characterBuilder.diceFaces(6L);
        } else if (type.equals(CharacterType.WEREWOLF)) {
            characterBuilder.characterType(type);
            characterBuilder.pv(34L);
            characterBuilder.force(7L);
            characterBuilder.defense(4L);
            characterBuilder.agility(7L);
            characterBuilder.diceQuantity(2L);
            characterBuilder.diceFaces(4L);
        } else {
            throw new CharacterTypeNotFoundException("Character Type Not Found");
        }

        return characterBuilder.build();
    }

    public static Character updateCharacter(UpdateCharacterDTO updateCharacterDTO, Character character) {
        character.setCharacterType(updateCharacterDTO.getCharacterType());
        character.setName(updateCharacterDTO.getName());
        character.setPv(updateCharacterDTO.getPv());
        character.setForce(updateCharacterDTO.getForce());
        character.setDefense(updateCharacterDTO.getDefense());
        character.setAgility(updateCharacterDTO.getAgility());
        character.setDiceQuantity(updateCharacterDTO.getDiceQuantity());
        character.setDiceFaces(updateCharacterDTO.getDiceFaces());
        return character;
    }

    public static Character randomCharacter() throws CharacterTypeNotFoundException {
        UUID name = UUID.randomUUID();
        CharacterType characterType = randomType();
        CharacterDTO characterDTO = new CharacterDTO(name.toString(), characterType);
        return createCharacter(characterDTO);
    }

    private static CharacterType randomType() {
        int pick = getRandomNumber(CharacterType.values().length);
        return CharacterType.values()[pick];
    }

    public static int getRandomNumber(int max) {
        return new Random().nextInt(max);
    }

    public static long calculateDamage(Character character) {
        int damage = 0;
        for (int i = 0; i < character.getDiceQuantity(); i++) {
            damage += getRandomNumber(character.getDiceFaces().intValue());
        }
        return damage;
    }
}
