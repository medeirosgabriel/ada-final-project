package com.medeirosgabriel.rpg.service;

import com.medeirosgabriel.rpg.dto.CreateMatchDTO;
import com.medeirosgabriel.rpg.exceptions.CharacterNotFoundException;
import com.medeirosgabriel.rpg.exceptions.CharacterTypeNotFoundException;
import com.medeirosgabriel.rpg.model.Character;
import com.medeirosgabriel.rpg.model.Match;
import com.medeirosgabriel.rpg.repository.MatchRepository;
import com.medeirosgabriel.rpg.util.CharacterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MatchService {

    private final CharacterService characterService;
    private final MatchRepository matchRepository;
    private final LogService logService;

    public Match createMatch(CreateMatchDTO createMatchDTO) throws CharacterNotFoundException, CharacterTypeNotFoundException {
        Character myCharacter = this.characterService.getCharacterById(createMatchDTO.getCharacterId());
        Character enemy = CharacterUtil.randomCharacter();
        Integer myNumber = CharacterUtil.getRandomNumber(20);
        Integer enemyNumber = CharacterUtil.getRandomNumber(20);
        Match match = new Match();
        if (myNumber >= enemyNumber) {
            String message = String.format("Match %d started | Character Chosen: %s | Enemy Id = %s | You start attacking",
                    match.getId(),
                    myCharacter.getName(),
                    enemy.getName());
            this.logService.createLog(message);
            match.setAttackTurn(1);
        } else {
            String message = String.format("Match %d started | Character Chosen: %s | Enemy Id = %s | Enemy start attacking",
                    match.getId(),
                    myCharacter.getName(),
                    enemy.getName());
            this.logService.createLog(message);
            match.setAttackTurn(2);
        }

        match.setMyCharacter(myCharacter);
        match.setEnemy(enemy);
        this.characterService.saveCharacter(enemy);
        this.matchRepository.save(match);

        return match;
    }
}
