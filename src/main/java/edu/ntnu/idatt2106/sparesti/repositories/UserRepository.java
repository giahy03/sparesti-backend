package edu.ntnu.idatt2106.sparesti.repositories;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities.
 * The interface extends JpaRepository, allowing basic CRUD
 * (Create, Read, Update, Delete) operations on users.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Retrieves user with the given email.
   *
   * @param email The email of the user to retrieve
   * @return An Optional containing the user if found, otherwise empty.
   */
  Optional<User> findUserByEmailIgnoreCase(String email);
}
