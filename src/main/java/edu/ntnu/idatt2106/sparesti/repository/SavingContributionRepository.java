package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingContribution;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Hanne-Sofie Søreide
 */
@Repository
public interface SavingContributionRepository extends JpaRepository<SavingContribution, Long> {

    List<SavingContribution> findAllContributionsByUser_Email(String email, Pageable pageable);
    SavingContribution findByUser_EmailAndGoal_Id(String email, long goalId);
    List<SavingContribution> findAllContributionsByGoal_Id(long goalId);

}
