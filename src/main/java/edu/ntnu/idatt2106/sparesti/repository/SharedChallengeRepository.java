package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing shared challenges.
 * The interface extends JpaRepository and allows basic
 * CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see SharedChallenge
 */
@Repository
public interface SharedChallengeRepository extends JpaRepository<SharedChallenge, Long> {
  List<SharedChallenge> findSharedChallengeBySharedChallengeCode_Id(long code);

  List<SharedChallenge> findSharedChallengeBySharedChallengeCode_JoinCode(String code);
}