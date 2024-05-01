package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingContribution;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for  contributions made to saving goals by users
 * The Jpa-interface supports basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Hanne-Sofie Søreide
 */
@Repository
public interface SavingContributionRepository extends JpaRepository<SavingContribution, Long> {

    List<SavingContribution> findAllContributionsByUser_Email(String email, Pageable pageable);
    Optional<SavingContribution> findByUser_EmailAndGoal_Id(String email, long goalId);
    List<SavingContribution> findAllContributionsByGoal_Id(long goalId);

}
