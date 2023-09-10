package com.medeirosgabriel.rpg.exceptions;

import com.medeirosgabriel.rpg.model.Match;

public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
