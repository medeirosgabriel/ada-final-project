package com.medeirosgabriel.rpg.service;

import com.medeirosgabriel.rpg.dto.CharacterDTO;
import com.medeirosgabriel.rpg.dto.UpdateCharacterDTO;
import com.medeirosgabriel.rpg.exceptions.CharacterNotFoundException;
import com.medeirosgabriel.rpg.exceptions.CharacterTypeNotFoundException;
import com.medeirosgabriel.rpg.model.Character;
import com.medeirosgabriel.rpg.repository.CharacterRepository;
import com.medeirosgabriel.rpg.util.CharacterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final LogService logService;

    public Character saveCharacter(CharacterDTO characterDTO) throws CharacterTypeNotFoundException {
        Character character = CharacterUtil.createCharacter(characterDTO);
        String message = String.format("Character %s created", character.getName());
        this.logService.createLog(message);
        return this.characterRepository.save(character);
    }

    public Character saveCharacter(Character character) {
        String message = String.format("Character %s created", character.getName());
        this.logService.createLog(message);
        return this.characterRepository.save(character);
    }

    public List<Character> listCharacters() {
        return this.characterRepository.findAll();
    }
    public Character updateCharacter(UpdateCharacterDTO updateCharacterDTO) throws CharacterNotFoundException {
        Character character = this.getCharacterById(updateCharacterDTO.getId());
        character = CharacterUtil.updateCharacter(updateCharacterDTO, character);
        return this.characterRepository.save(character);
    }

    public void deleteCharacter(Long id) throws CharacterNotFoundException {
        Optional<Character> character = this.characterRepository.findById(id);
        if (character.isEmpty()) {
            throw new CharacterNotFoundException("Character Not Found");
        }
        String message = String.format("Character %s removed", character.get().getName());
        this.logService.createLog(message);
        this.characterRepository.deleteById(id);
    }
    public Character getCharacterById(Long id) throws CharacterNotFoundException {
        Optional<Character> character = this.characterRepository.findById(id);
        if (character.isEmpty()) {
            throw new CharacterNotFoundException("Character Not Found");
        }
        return this.characterRepository.findById(id).get();
    }
}
