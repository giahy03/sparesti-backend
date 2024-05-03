package edu.ntnu.idatt2106.sparesti.model.saving;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2106.sparesti.model.goal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;




/**
 * Test class for the SavingGoal model class.
 *
 * @author Hanne-Sofie Søreide
 */
class SavingGoalTest {

  SavingGoal goal;

  @BeforeEach
  void setup() {
    goal = SavingGoalUtility.createSavingGoalA();
  }

  @DisplayName("JUnit test for the getter methods")
  @Test
  void goal_getGoal_returnsExpectedValues() {
    // Arrange
    final Long expectedId = 1L;
    final String expectedTitle = "Goal";
    final LocalDate expectedStartDate = LocalDate.of(2024, 4, 15);
    final LocalDate expectedEndDate = LocalDate.of(2024, 5, 3);
    final int expectedLives = 3;
    final GoalState expectedState = GoalState.UNDER_PROGRESS;
    final double expectedAmount = 10000.0;
    final User expectedAuthor = SavingGoalUtility.createUserA();


    // Assert
    assertEquals(expectedId, goal.getId());
    assertEquals(expectedTitle, goal.getTitle());
    assertEquals(expectedStartDate, goal.getStartDate());
    assertEquals(expectedEndDate, goal.getEndDate());
    assertEquals(expectedLives, goal.getLives());
    assertEquals(expectedState, goal.getState());
    assertEquals(expectedAmount, goal.getTotalAmount());
    assertEquals(expectedAuthor.getEmail(), goal.getAuthor().getEmail());
  }


  @DisplayName("JUnit test for the setter methods")
  @Test
  void goal_SetGoal_ReturnsExpectedValues() {

    // Arrange
    String expectedName = "Second Goal";
    LocalDate expectedStartDate = LocalDate.of(2024, 4, 20);
    LocalDate expectedEndDate = LocalDate.of(2024, 5, 1);
    int expectedLives = 2;
    GoalState expectedState = GoalState.UNDER_PROGRESS;
    double expectedAmount = 2000.0;
    User expectedUser = SavingGoalUtility.createUserB();


    // Act
    goal.setTitle(expectedName);
    goal.setStartDate(expectedStartDate);
    goal.setEndDate(expectedEndDate);
    goal.setLives(expectedLives);
    goal.setState(expectedState);
    goal.setTotalAmount(expectedAmount);

    // Assert
    assertEquals(expectedName, goal.getTitle());
    assertEquals(expectedStartDate, goal.getStartDate());
    assertEquals(expectedEndDate, goal.getEndDate());
    assertEquals(expectedLives, goal.getLives());
    assertEquals(expectedState, goal.getState());
    assertEquals(expectedAmount, goal.getTotalAmount());

  }

  @DisplayName("JUnit test for the setters expecting exceptions setting Null")
  @Test
  void goal_SetGoalWithNull_ThrowsException() {
    // Act and Assert
    assertThrows(NullPointerException.class, () -> goal.setTitle(null));
    assertThrows(NullPointerException.class, () -> goal.setState(null));
    assertThrows(NullPointerException.class, () -> goal.setStartDate(null));
    assertThrows(NullPointerException.class, () -> goal.setEndDate(null));
  }

}