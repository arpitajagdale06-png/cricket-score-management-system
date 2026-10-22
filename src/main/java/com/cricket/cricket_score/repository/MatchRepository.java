
package com.cricket.cricket_score.repository;

import com.cricket.cricket_score.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByStatus(String status);
}
