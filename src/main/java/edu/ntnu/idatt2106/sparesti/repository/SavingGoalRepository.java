package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing saving goals.
 * The interface extends JpaRepository allowing basic CRUD
 * (Create, Read, Update, Delete) operations.
 *
 * @author Hanne-Sofie Søreide
 * @version 1.0
 */
@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {
  Optional<SavingGoal> findById(long id);

  Optional<SavingGoal> findByJoinCode(String code);

}
