package com.medeirosgabriel.rpg.controller;

import com.medeirosgabriel.rpg.dto.CreateBattleDTO;
import com.medeirosgabriel.rpg.dto.BattleActionDTO;
import com.medeirosgabriel.rpg.model.Battle;
import com.medeirosgabriel.rpg.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/match")
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody CreateBattleDTO createMatchDTO) {
        try {
            Battle battle = this.matchService.createMatch(createMatchDTO);
            return new ResponseEntity<>(battle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/attack")
    public ResponseEntity<String> attack(@RequestBody BattleActionDTO battleActionDTO) {
        try {
            String response = this.matchService.attack(battleActionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/calculate")
    public ResponseEntity<String> calculate(@RequestBody BattleActionDTO battleActionDTO) {
        try {
            String response = this.matchService.calculate(battleActionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }


    @PostMapping(value = "/defense")
    public ResponseEntity<String> defense(@RequestBody BattleActionDTO battleActionDTO) {
        try {
            String response = this.matchService.defense(battleActionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}
