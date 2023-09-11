package com.medeirosgabriel.rpg.service;

import com.medeirosgabriel.rpg.model.Log;
import com.medeirosgabriel.rpg.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    public Log createLog(String message) {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        Log log = new Log(message, todayAsString);
        return this.logRepository.save(log);
    }

    public List<Log> listLogs() {
        return this.logRepository.findAll();
    }
}
