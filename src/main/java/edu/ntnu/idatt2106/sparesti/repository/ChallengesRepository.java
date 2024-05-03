package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing challenges.
 * The interface extends JpaRepository allowing basic CRUD
 * (Create, Read, Update, Delete) operations.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Repository
public interface ChallengesRepository extends JpaRepository<Challenge, Long> {
  List<Challenge> findByUser_Email(String email, Pageable pageable);

}