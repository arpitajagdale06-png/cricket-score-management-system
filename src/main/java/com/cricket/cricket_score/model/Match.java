
package com.cricket.cricket_score.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public class Match {

    @Id
    private String id;

    private String teamA;
    private String teamB;
    private int runs;
    private int wickets;
    private int balls;
    private String status;

    public Match() {
    }

    public Match(String teamA, String teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.runs = 0;
        this.wickets = 0;
        this.balls = 0;
        this.status = "LIVE";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOvers() {
        return (balls / 6) + "." + (balls % 6);
    }
}
