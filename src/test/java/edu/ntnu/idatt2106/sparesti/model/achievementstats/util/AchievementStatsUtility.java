package edu.ntnu.idatt2106.sparesti.model.achievementstats.util;

import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.util.List;

/**
 * Utility class for creating AchievementStats objects for testing.
 *
 * @see AchievementStats
 * @author Jeffrey Yaw Annor Tabiri
 */
public class AchievementStatsUtility {

  /**
   * Creates an AchievementStats object for testing.
   *
   * @return an AchievementStats object
   */
  public static AchievementStats createAchievementStatsA() {

    return AchievementStats.builder()
            .id(1L)
            .challengesAchieved(2)
            .savingGoalsAchieved(1)
            .readNews(false)
            .streak(9)
            .totalSaved(99.0)
            .user(createUserA())
            .build();
  }

  /**
   * Creates an AchievementStats object for testing.
   *
   * @return an AchievementStats object
   */
  public static User createUserA() {
    return User.builder()
            .email("example@email")
            .firstName("Kari")
            .role(Role.USER)
            .lastName("Normann")
            .password("password")
            .build();
  }

  /**
   * Creates an AchievementStats object for testing.
   *
   * @return an AchievementStats object
   */
  public static User createUserB() {
    return User.builder()
            .email("example2@email")
            .firstName("Kari")
            .role(Role.USER)
            .lastName("Normann")
            .password("password")
            .build();
  }

  /**
   * Creates an AchievementStats object for testing.
   *
   * @return an AchievementStats object
   */
  public static Achievement createAchievement() {
    return Achievement.builder()
            .description("Saving streak")
            .thresholds(List.of(1, 2, 3, 4))
            .category(AchievementCategory.AMOUNT_SAVED)
            .id(1)
            .build();
  }

}
