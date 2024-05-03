package edu.ntnu.idatt2106.sparesti.repository;


import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing badges. The interface extends JpaRepository and allows basic
 * CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Hanne-Sofie Søreide
 * @version 1.0
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

  Optional<Achievement> findById(int id);

  Optional<Achievement> findByCategory(AchievementCategory category);

}
