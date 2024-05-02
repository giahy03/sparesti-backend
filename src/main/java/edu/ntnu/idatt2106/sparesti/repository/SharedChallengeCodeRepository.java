package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing shared challenge codes.
 * The interface extends JpaRepository and allows basic
 * CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Repository
public interface SharedChallengeCodeRepository extends JpaRepository<SharedChallengeCode, Long> {
}