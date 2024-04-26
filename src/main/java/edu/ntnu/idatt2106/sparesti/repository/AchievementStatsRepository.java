package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AchievementStatsRepository extends JpaRepository<AchievementStats, Long> {

    Optional<AchievementStats> findAchievementStatsByUserEmail(String email);

}
