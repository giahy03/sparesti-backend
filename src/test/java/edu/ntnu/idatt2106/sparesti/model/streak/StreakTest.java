package edu.ntnu.idatt2106.sparesti.model.streak;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Test class for the Streak class.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
class StreakTest {
  User user;
  Streak streak;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUser1();
    streak = ChallengeUtility.createStreak1();

    user.setStreak(streak);
    streak.setUser(user);
  }

  @Test
  @DisplayName("Test that streak constructor returns a streak object")
  void streak_AllArgsConstructor_ReturnCorrectStreak() {
    //Arrange
    int expectedNumberOfDays = 20;
    User expectedUser = ChallengeUtility.createUser1();
    long expectedStreakId = 1L;


    //Act
    Streak actualStreak = Streak.builder()
            .streakId(expectedStreakId)
            .numberOfDays(expectedNumberOfDays)
            .user(expectedUser)
            .build();


    //Assert
    assertEquals(expectedStreakId, actualStreak.getStreakId());
    assertEquals(expectedNumberOfDays, actualStreak.getNumberOfDays());
    assertEquals(expectedUser, actualStreak.getUser());
  }


  @Test
  @DisplayName("Test that no args streak constructor returns a streak object")
  void streak_NoArgsConstructor_ReturnStreak() {
    //Arrange
    int expectedNumberOfDays = 20;
    User expectedUser = ChallengeUtility.createUser1();

    //Act
    Streak actualStreak = new Streak();
    actualStreak.setNumberOfDays(expectedNumberOfDays);
    actualStreak.setUser(expectedUser);

    //Assert
    assertEquals(expectedNumberOfDays, actualStreak.getNumberOfDays());
    assertEquals(expectedUser, actualStreak.getUser());
  }

}