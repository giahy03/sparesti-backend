package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengesRepository extends JpaRepository<Challenge, Long> {
  List<Challenge> findAllByUser_Username(String username, Pageable pageable);

}