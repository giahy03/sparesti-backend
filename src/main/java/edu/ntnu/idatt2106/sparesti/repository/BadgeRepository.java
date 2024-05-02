package edu.ntnu.idatt2106.sparesti.repository;



import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing badges of a user.
 * The Jpa-interface supports basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Hanne-Sofie Søreide
 * @version 1.0
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
  List<Badge> findAllByUser_Email(String email, Pageable pageable);

  Badge findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(
          String email, AchievementCategory achievementCategory);
}
