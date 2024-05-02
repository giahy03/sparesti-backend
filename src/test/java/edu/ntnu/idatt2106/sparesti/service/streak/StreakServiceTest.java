package edu.ntnu.idatt2106.sparesti.service.streak;

import edu.ntnu.idatt2106.sparesti.dto.streak.StreakResponseDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.streak.Streak;
import edu.ntnu.idatt2106.sparesti.model.streak.StreakUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Test class for the StreakService class.
 * The class contains tests for the methods in the StreakService class.
 *
 * @version 1.0
 * @see StreakService
 * @author Jeffrey Yaw Annor Tabiri
 */
@ExtendWith(MockitoExtension.class)
class StreakServiceTest {

  @InjectMocks
  private StreakService streakService;

  @Mock
  UserService userService;

  @Mock
  UserRepository userRepository;

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
  void Service_UpdateStreak_IncreasesStreakCorrectly() {
    //Arrange
    int oldStreak = streak.getNumberOfDays();
    when(userService.findUser(user.getEmail())).thenReturn(user);

    streakService.updateStreak(StreakUtility.createStreakRequestDto1(), user.getEmail());
    int newStreak = streak.getNumberOfDays();

    //Assert
    verify(userRepository).save(user);
    assertTrue(oldStreak < newStreak);
  }

  @Test
  void Service_UpdateStreak_DoesNotIncreaseStreak() {
    //Arrange
    int expectedValue = 0;
    when(userService.findUser(user.getEmail())).thenReturn(user);

    streakService.updateStreak(StreakUtility.createStreakRequestDto2(), user.getEmail());
    int actualValue = streak.getNumberOfDays();

    //Assert
    verify(userRepository).save(user);
    assertEquals(expectedValue, actualValue);
  }

  @Test
  void Service_GetStreak_ReturnsCorrectStreak() {
    //Arrange
    when(userService.findUser(user.getEmail())).thenReturn(user);

    StreakResponseDto streakResponseDto = streakService.getStreak(user.getEmail());

    //Assert
    assertEquals(streak.getNumberOfDays(), streakResponseDto.getNumberOfDays());
  }

  @Test
  void Service_InitializeStreak_ReturnsInitializedStreak() {
    //Arrange
    user.setStreak(null);
    when(userService.findUser(user.getEmail())).thenReturn(user);

    streakService.updateStreak(StreakUtility.createStreakRequestDto1(), user.getEmail());

    //Assert
    assertEquals(1, user.getStreak().getNumberOfDays());
  }
}