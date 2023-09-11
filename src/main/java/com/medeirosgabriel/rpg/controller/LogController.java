package com.medeirosgabriel.rpg.controller;

import com.medeirosgabriel.rpg.model.Character;
import com.medeirosgabriel.rpg.model.Log;
import com.medeirosgabriel.rpg.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/log")
public class LogController {

    private final LogService logService;
    @GetMapping
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = this.logService.listLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
