package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing the achievement stats of a user.
 * The Jpa-interface supports basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Hanne-Sofie Søreide
 * @version 1.0
 */
@Repository
public interface AchievementStatsRepository extends JpaRepository<AchievementStats, Long> {

  Optional<AchievementStats> findAchievementStatsByUserEmail(String email);

}
