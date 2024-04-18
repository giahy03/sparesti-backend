package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing challenges.
 * The interface extends JpaRepository allowing basic CRUD
 * (Create, Read, Update, Delete) operations.
 *
 * @see Challenge
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
public interface ChallengesRepository extends JpaRepository<Challenge, Long> {
  List<Challenge> findByUser_Email(String email, Pageable pageable);

}