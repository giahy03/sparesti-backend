package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;


import java.util.Optional;

/**
 * Repository interface for managing saving goals.
 * The interface extends JpaRepository allowing basic CRUD
 * (Create, Read, Update, Delete) operations.
 *
 * @author Hanne-Sofie Søreide
 */
@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {
    Optional<SavingGoal> findById(long id);

    List<SavingGoal> findAllByUser_Username(String email, Pageable pageable);

    Optional<SavingGoal> findByJoinCode(String code);

    //List<SavingGoal> findAllByUser_Username_AndAchievedIsTrue(String email, Pageable pageable);

}
