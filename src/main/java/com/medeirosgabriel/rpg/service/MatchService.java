package com.medeirosgabriel.rpg.service;

import com.medeirosgabriel.rpg.dto.CreateMatchDTO;
import com.medeirosgabriel.rpg.dto.MatchActionDTO;
import com.medeirosgabriel.rpg.enums.NextStep;
import com.medeirosgabriel.rpg.exceptions.CharacterNotFoundException;
import com.medeirosgabriel.rpg.exceptions.CharacterTypeNotFoundException;
import com.medeirosgabriel.rpg.exceptions.MatchNotFoundException;
import com.medeirosgabriel.rpg.exceptions.TurnException;
import com.medeirosgabriel.rpg.model.Character;
import com.medeirosgabriel.rpg.model.Match;
import com.medeirosgabriel.rpg.repository.MatchRepository;
import com.medeirosgabriel.rpg.util.CharacterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            match.setNextStep(NextStep.ATTACK);
        } else {
            String message = String.format("Match %d started | Character Chosen: %s | Enemy Id = %s | Enemy start attacking",
                    match.getId(),
                    myCharacter.getName(),
                    enemy.getName());
            this.logService.createLog(message);
            match.setNextStep(NextStep.DEFENSE);
        }

        match.setMyCharacter(myCharacter);
        match.setEnemy(enemy);
        this.characterService.saveCharacter(enemy);
        this.matchRepository.save(match);

        return match;
    }

    public String attack(MatchActionDTO matchActionDTO) throws MatchNotFoundException, TurnException {
        Long id = matchActionDTO.getMatchId();
        Optional<Match> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            throw new MatchNotFoundException("Match not found");
        } else {
            Match match = opt.get();
            if (match.getNextStep().equals(NextStep.ATTACK)) {
                Character myCharacter = match.getMyCharacter();
                Character enemy = match.getEnemy();
                Long attack = CharacterUtil.getRandomNumber(12) + myCharacter.getForce() + myCharacter.getAgility();
                Long defense = CharacterUtil.getRandomNumber(12) + enemy.getDefense() + enemy.getAgility();
                if (attack > defense) {
                    match.setNextStep(NextStep.CALCULATE_DAMAGE);
                    return "Calculate your damage";
                } else {
                    match.setNextStep(NextStep.DEFENSE);
                    return "The enemy defended itself";
                }
            } else {
                throw new TurnException("It's not your turn to attack");
            }
        }
    }

    public String defense(MatchActionDTO matchActionDTO) throws MatchNotFoundException, TurnException {
        Long id = matchActionDTO.getMatchId();
        Optional<Match> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            throw new MatchNotFoundException("Match not found");
        } else {
            Match match = opt.get();
            if (match.getNextStep().equals(NextStep.DEFENSE)) {
                Character myCharacter = match.getMyCharacter();
                Character enemy = match.getEnemy();
                Long attack = CharacterUtil.getRandomNumber(12) + enemy.getForce() + enemy.getAgility();
                Long defense = CharacterUtil.getRandomNumber(12) + myCharacter.getDefense() + myCharacter.getAgility();
                if (attack > defense) {
                    // IMPLEMENT
                } else {
                    match.setNextStep(NextStep.ATTACK);
                    return "You defended yourself from the attack";
                }
            } else {
                throw new TurnException("It's not your turn to defense");
            }
        }
    }

    public String calculate(MatchActionDTO matchActionDTO) throws MatchNotFoundException, TurnException {
        Long id = matchActionDTO.getMatchId();
        Optional<Match> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            throw new MatchNotFoundException("Match not found");
        } else {
            Match match = opt.get();
            if (match.getNextStep().equals(NextStep.DEFENSE)) {
                Character myCharacter = match.getMyCharacter();
                Character enemy = match.getEnemy();
                // IMPLEMENT
            } else {
                throw new TurnException("It's not your turn to calculate damage");
            }
        }
    }
}
