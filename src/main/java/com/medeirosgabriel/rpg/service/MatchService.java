package com.medeirosgabriel.rpg.service;

import com.medeirosgabriel.rpg.dto.CreateBattleDTO;
import com.medeirosgabriel.rpg.dto.BattleActionDTO;
import com.medeirosgabriel.rpg.enums.NextStep;
import com.medeirosgabriel.rpg.exceptions.*;
import com.medeirosgabriel.rpg.model.Character;
import com.medeirosgabriel.rpg.model.Battle;
import com.medeirosgabriel.rpg.repository.BattleRepository;
import com.medeirosgabriel.rpg.util.CharacterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchService {

    private final CharacterService characterService;
    private final BattleRepository matchRepository;
    private final LogService logService;

    public Battle createMatch(CreateBattleDTO createBattleDTO) throws CharacterNotFoundException, CharacterTypeNotFoundException, CreateBattleException {
        Character myCharacter = this.characterService.getCharacterById(createBattleDTO.getCharacterId());
        if (myCharacter.isAlive()) {
            Character enemy = CharacterUtil.randomCharacter();
            Integer myNumber = CharacterUtil.getRandomNumber(20);
            Integer enemyNumber = CharacterUtil.getRandomNumber(20);
            Battle match = new Battle();
            if (myNumber >= enemyNumber) {
                String message = String.format("Battle %d started | Character Chosen: %s | Enemy Id = %s | You start attacking",
                        match.getId(),
                        myCharacter.getName(),
                        enemy.getName());
                this.logService.createLog(message);
                match.setNextStep(NextStep.ATTACK);
            } else {
                String message = String.format("Battle %d started | Character Chosen: %s | Enemy Id = %s | Enemy start attacking",
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
            String message = "Your character is dead. Create other character and start a battle";
            this.logService.createLog(message);
            throw new CreateBattleException(message);
        }
    }

    public String attack(BattleActionDTO battleActionDTO) throws BattleNotFoundException, TurnException {
        Long id = battleActionDTO.getBattleId();
        Optional<Battle> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            String message = String.format("Battle %d not found", id);
            this.logService.createLog(message);
            throw new BattleNotFoundException(message);
        } else if (opt.get().getNextStep().equals(NextStep.FINISHED)){
            String message = String.format("You lost the battle %d", id);
            this.logService.createLog(message);
            throw new BattleNotFoundException(message);
        } else {
            Battle match = opt.get();
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

    public String defense(BattleActionDTO battleActionDTO) throws BattleNotFoundException, TurnException {
        Long id = battleActionDTO.getBattleId();
        Optional<Battle> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            String message = String.format("Battle %d not found", id);
            this.logService.createLog(message);
            throw new BattleNotFoundException(message);
        } else if (opt.get().getNextStep().equals(NextStep.FINISHED)){
            String message = String.format("You lost the battle %d", id);
            this.logService.createLog(message);
            throw new BattleNotFoundException(message);
        } else {
            Battle match = opt.get();
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

    public String calculate(BattleActionDTO battleActionDTO) throws BattleNotFoundException, TurnException {
        Long id = battleActionDTO.getBattleId();
        Optional<Battle> opt = this.matchRepository.findById(id);
        if (opt.isEmpty()) {
            String message = String.format("Battle %d not found", id);
            this.logService.createLog(message);
            throw new BattleNotFoundException(message);
        } else if (opt.get().getNextStep().equals(NextStep.FINISHED)){
            String message = String.format("You lost the battle %d", id);
            this.logService.createLog(message);
            throw new BattleNotFoundException(message);
        } else {
            Battle match = opt.get();
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
