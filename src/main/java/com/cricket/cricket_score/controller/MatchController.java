
package com.cricket.cricket_score.controller;

import com.cricket.cricket_score.model.Match;
import com.cricket.cricket_score.repository.MatchRepository;
import com.cricket.cricket_score.service.ScoreService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "http://localhost:5173")
public class MatchController {

    private final MatchRepository repository;
    private final ScoreService scoreService;

    public MatchController(
            MatchRepository repository,
            ScoreService scoreService) {
        this.repository = repository;
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Match> getAllMatches() {
        return repository.findAll();
    }

    @GetMapping("/live")
    public List<Match> getLiveMatches() {
        return repository.findByStatus("LIVE");
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Match not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Match createMatch(@RequestBody Match match) {
        match.setId(null);
        match.setRuns(0);
        match.setWickets(0);
        match.setBalls(0);
        match.setStatus("LIVE");
        return repository.save(match);
    }

    @PostMapping("/{id}/runs")
    public Match addRuns(
            @PathVariable String id,
            @RequestParam int runs) {
        return scoreService.addRuns(id, runs);
    }

    @PostMapping("/{id}/wicket")
    public Match addWicket(@PathVariable String id) {
        return scoreService.addWicket(id);
    }
}
