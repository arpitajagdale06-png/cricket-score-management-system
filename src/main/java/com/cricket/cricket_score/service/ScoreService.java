
package com.cricket.cricket_score.service;

import com.cricket.cricket_score.model.Match;
import com.cricket.cricket_score.repository.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ScoreService {

    private final MatchRepository repository;

    public ScoreService(MatchRepository repository) {
        this.repository = repository;
    }

    public Match addRuns(String id, int runs) {
        Match match = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Match not found"));

        if (!match.getStatus().equals("LIVE")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Match is not live");
        }

        if (runs != 0 && runs != 1 && runs != 2
                && runs != 3 && runs != 4 && runs != 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid run value");
        }

        match.setRuns(match.getRuns() + runs);
        match.setBalls(match.getBalls() + 1);

        return repository.save(match);
    }

    public Match addWicket(String id) {
        Match match = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Match not found"));

        if (!match.getStatus().equals("LIVE")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Match is not live");
        }

        if (match.getWickets() >= 10) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "All wickets have fallen");
        }

        match.setWickets(match.getWickets() + 1);
        match.setBalls(match.getBalls() + 1);

        return repository.save(match);
    }
}
