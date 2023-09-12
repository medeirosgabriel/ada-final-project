package com.medeirosgabriel.rpg.controller;

import com.medeirosgabriel.rpg.dto.CharacterDTO;
import com.medeirosgabriel.rpg.dto.UpdateCharacterDTO;
import com.medeirosgabriel.rpg.model.Character;
import com.medeirosgabriel.rpg.service.CharacterService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/character")
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public ResponseEntity<?> saveCharacter(@RequestBody CharacterDTO characterDTO) {
        try {
            Character character = this.characterService.saveCharacter(characterDTO);
            return ResponseEntity.ok(character);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Character>> listCharacters() {
        List<Character> characters = this.characterService.listCharacters();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateCharacter(@RequestBody UpdateCharacterDTO updateCharacterDTO) {
        try {
            Character newCharacter = this.characterService.updateCharacter(updateCharacterDTO);
            return new ResponseEntity<>(String.format("Character %d updated", newCharacter.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable("id") Long id) {
        try {
            this.characterService.deleteCharacter(id);
            return new ResponseEntity<>(String.format("Character %d removed", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCharacterById(@PathVariable("id") Long id) {
        try {
            Character character = this.characterService.getCharacterById(id);
            return new ResponseEntity<>(character, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
