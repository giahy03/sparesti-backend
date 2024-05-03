package edu.ntnu.idatt2106.sparesti.model.achievementstats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import edu.ntnu.idatt2106.sparesti.model.achievementstats.util.AchievementStatsUtility;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Test class for the achievement stats.
 *
 * @author Hanne-Sofie Søreide
 * @see AchievementStats
 */
class AchievementStatsTest {
  AchievementStats achievementStats;
  static User user;

  @BeforeAll
  static void beforeClass() {
    user = AchievementStatsUtility.createUserA();
  }

  @BeforeEach
  void setup() {
    achievementStats = AchievementStatsUtility.createAchievementStatsA();
  }

  @DisplayName("Test builder of achievement stats entity")
  @Test
  void achievementStats_Constructor() {

    // Arrange
    AchievementStats testStats = AchievementStats.builder()
            .challengesAchieved(10)
            .savingGoalsAchieved(10)
            .readNews(false)
            .totalSaved(100.0)
            .streak(10)
            .user(user)
            .build();

    // Act
    final int actualChallenges = testStats.getChallengesAchieved();
    final int actualGoals = testStats.getSavingGoalsAchieved();
    final int actualStreak = testStats.getStreak();
    final double actualSaved = testStats.getTotalSaved();
    final boolean actualNews = testStats.isReadNews();
    final User actualUser = testStats.getUser();

    // Assert
    assertEquals(10, actualChallenges);
    assertEquals(10, actualGoals);
    assertFalse(actualNews);
    assertEquals(10, actualStreak);
    assertEquals(100.0, actualSaved);
    assertEquals(user, actualUser);

  }

  @DisplayName("Test setter methods of achievement stats entity")
  @Test
  void achievementStats_SetterReturnCorrectValues() {

    // Arrange
    User expectedUser = AchievementStatsUtility.createUserB();
    int expectedChallenges = 20;
    int expectedGoals = 30;
    int expectedStreak = 40;
    double expectedSaved = 50.0;
    boolean expectedNews = true;

    // Act
    achievementStats.setChallengesAchieved(expectedChallenges);
    achievementStats.setSavingGoalsAchieved(expectedGoals);
    achievementStats.setStreak(expectedStreak);
    achievementStats.setUser(expectedUser);
    achievementStats.setTotalSaved(expectedSaved);
    achievementStats.setReadNews(expectedNews);

    // Assert
    assertEquals(expectedChallenges, achievementStats.getChallengesAchieved());
    assertEquals(expectedGoals, achievementStats.getSavingGoalsAchieved());
    assertEquals(expectedStreak, achievementStats.getStreak());
    assertEquals(expectedSaved, achievementStats.getTotalSaved());
    assertEquals(expectedNews, achievementStats.isReadNews());
    assertEquals(expectedUser, achievementStats.getUser());

  }

}
