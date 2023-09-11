package com.medeirosgabriel.rpg.service;

import com.medeirosgabriel.rpg.dto.CreateMatchDTO;
import com.medeirosgabriel.rpg.dto.MatchActionDTO;
import com.medeirosgabriel.rpg.enums.NextStep;
import com.medeirosgabriel.rpg.exceptions.*;
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

    public Match createMatch(CreateMatchDTO createMatchDTO) throws CharacterNotFoundException, CharacterTypeNotFoundException, CreateMatchException {
        Character myCharacter = this.characterService.getCharacterById(createMatchDTO.getCharacterId());
        if (myCharacter.isAlive()) {
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

        } else {
            String message = "Your character is dead. Create other  character";
            this.logService.createLog(message);
            throw new CreateMatchException(message);
        }
    }

    public String attack(MatchActionDTO matchActionDTO) throws MatchNotFoundException, TurnException {
        Long id = matchActionDTO.getMatchId();
        Optional<Match> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            String message = "Match not found";
            this.logService.createLog(message);
            throw new MatchNotFoundException(message);
        } else {
            Match match = opt.get();
            if (match.getNextStep().equals(NextStep.ATTACK)) {
                Character myCharacter = match.getMyCharacter();
                Character enemy = match.getEnemy();
                Long attack = CharacterUtil.getRandomNumber(12) + myCharacter.getForce() + myCharacter.getAgility();
                Long defense = CharacterUtil.getRandomNumber(12) + enemy.getDefense() + enemy.getAgility();
                if (attack > defense) {
                    match.setNextStep(NextStep.CALCULATE_DAMAGE);
                    this.matchRepository.save(match);

                    String message = String.format("Attack: %d Defense %d. Calculate your damage", attack, defense);
                    this.logService.createLog(message);
                    return message;
                } else {
                    match.setNextStep(NextStep.DEFENSE);
                    this.matchRepository.save(match);

                    String message = String.format("Attack: %d Defense %d. The enemy defended itself from the attack", attack, defense);
                    this.logService.createLog(message);
                    return message;
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
            String message = "Match not found";
            this.logService.createLog(message);
            throw new MatchNotFoundException(message);
        } else {
            Match match = opt.get();
            if (match.getNextStep().equals(NextStep.DEFENSE)) {
                Character myCharacter = match.getMyCharacter();
                Character enemy = match.getEnemy();
                Long attack = CharacterUtil.getRandomNumber(12) + enemy.getForce() + enemy.getAgility();
                Long defense = CharacterUtil.getRandomNumber(12) + myCharacter.getDefense() + myCharacter.getAgility();
                if (attack > defense) {
                    long enemyDamage = CharacterUtil.calculateDamage(enemy);
                    myCharacter.takeDamage(enemyDamage);
                    if (myCharacter.isAlive()) {
                        match.setNextStep(NextStep.ATTACK);
                        this.matchRepository.save(match);

                        String message = String.format("You took %d damage from %s", enemyDamage, enemy.getName());
                        this.logService.createLog(message);
                        return message;
                    } else {
                        match.setNextStep(NextStep.FINISHED);
                        this.matchRepository.save(match);

                        String message = String.format("You were killed with %d damage from opponent %s", enemyDamage, enemy.getName());
                        this.logService.createLog(message);
                        return message;
                    }
                } else {
                    match.setNextStep(NextStep.ATTACK);
                    this.matchRepository.save(match);

                    String message = String.format("You defended yourself from the %s attack", enemy.getName());
                    this.logService.createLog(message);
                    return message;
                }
            } else {
                String message = "It's not your turn to defense";
                this.logService.createLog(message);
                return message;
            }
        }
    }

    public String calculate(MatchActionDTO matchActionDTO) throws MatchNotFoundException, TurnException {
        Long id = matchActionDTO.getMatchId();
        Optional<Match> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            String message = "Match not found";
            this.logService.createLog(message);
            throw new MatchNotFoundException(message);
        } else {
            Match match = opt.get();
            if (match.getNextStep().equals(NextStep.CALCULATE_DAMAGE)) {
                Character myCharacter = match.getMyCharacter();
                Character enemy = match.getEnemy();
                long myDamage = CharacterUtil.calculateDamage(myCharacter);
                enemy.takeDamage(myDamage);
                if (enemy.isAlive()) {
                    match.setNextStep(NextStep.DEFENSE);
                    this.matchRepository.save(match);

                    String message = String.format("The enemy %s took %d damage from you", enemy.getName(), myDamage);
                    this.logService.createLog(message);
                    return message;
                } else {
                    match.setNextStep(NextStep.FINISHED);
                    this.matchRepository.save(match);

                    String message = String.format("The enemy %s was killed by you with %d damage", enemy.getName(), myDamage);
                    this.logService.createLog(message);
                    return message;
                }
            } else {
                String message = "It's not your turn to calculate damage";
                this.logService.createLog(message);
                throw new TurnException(message);
            }
        }
    }
}
