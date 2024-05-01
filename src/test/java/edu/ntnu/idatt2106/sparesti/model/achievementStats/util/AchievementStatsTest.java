package edu.ntnu.idatt2106.sparesti.model.achievementStats.util;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for the achievement stats.
 *
 * @see AchievementStats
 * @author Hanne-Sofie Søreide
 */
public class AchievementStatsTest {
    AchievementStats achievementStats;
    static User user;

    @BeforeAll
    static void beforeClass() {
        user = AchievementStatsUtility.createUserA();
    }

    @BeforeEach
    void setup() {
        achievementStats = AchievementStatsUtility.createAchievementStats();
    }


    @Test
    void AchievementStats_Constructor() {

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
        int actualChallenges = testStats.getChallengesAchieved();
        int actualGoals = testStats.getSavingGoalsAchieved();
        int actualStreak = testStats.getStreak();
        double actualSaved = testStats.getTotalSaved();
        boolean actualNews = testStats.isReadNews();
        User actualUser = testStats.getUser();

        // Assert
        assertEquals(10, actualChallenges);
        assertEquals(10, actualGoals);
        assertFalse(actualNews);
        assertEquals(10, actualStreak);
        assertEquals(100.0, actualSaved);
        assertEquals(user, actualUser);

    }

    @Test
    void AchievementStats_SetterReturnCorrectValues() {

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
