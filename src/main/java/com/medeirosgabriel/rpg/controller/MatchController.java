package com.medeirosgabriel.rpg.controller;

import com.medeirosgabriel.rpg.dto.CreateMatchDTO;
import com.medeirosgabriel.rpg.dto.MatchActionDTO;
import com.medeirosgabriel.rpg.exceptions.MatchNotFoundException;
import com.medeirosgabriel.rpg.model.Match;
import com.medeirosgabriel.rpg.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/match")
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody CreateMatchDTO createMatchDTO) {
        try {
            Match match = this.matchService.createMatch(createMatchDTO);
            return new ResponseEntity<>(match, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/attack")
    public ResponseEntity<String> attack(@RequestBody MatchActionDTO matchActionDTO) {
        try {
            String response = this.matchService.attack(matchActionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/calculate")
    public ResponseEntity<String> calculate(@RequestBody MatchActionDTO matchActionDTO) {
        try {
            String response = this.matchService.calculate(matchActionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }


    @PostMapping(value = "/defense")
    public ResponseEntity<String> defense(@RequestBody MatchActionDTO matchActionDTO) {
        try {
            String response = this.matchService.defense(matchActionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}
