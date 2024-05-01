package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing EmailCode entities
 * The interface extends JpaRepository, allowing basic CRUD
 * (Create, Read, Update, Delete) operations on the entities.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @author Ramtin Samavat
 * @version 1.0
 */
@Repository
public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {

  /**
   * Retrieves an EmailCode entity by email.
   *
   * @param email the email to search for
   * @return an Optional containing the EmailCode if found, otherwise empty.
   */
  Optional<EmailCode> findByEmail(String email);

  /**
   * Deletes all EmailCode entities with an expiry timestamp before the given currentDateTime.
   *
   * @param currentDateTime the current date and time
   */
  void deleteAllByExpiryTimestamp(LocalDateTime currentDateTime);

}
