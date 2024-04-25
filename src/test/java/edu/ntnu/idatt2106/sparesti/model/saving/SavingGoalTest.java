package edu.ntnu.idatt2106.sparesti.model.saving;

import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the SavingGoal model class.
 *
 * @author Hanne-Sofie Søreide
 */

// OBS: Excluded user for now.
public class SavingGoalTest {

    SavingGoal goal;

    @BeforeEach
    void setup() {
        goal = SavingGoalUtility.createSavingGoalA();
    }

    @DisplayName("JUnit test for the getter methods")
    @Test
    void Goal_getters_returnsExpectedValues() {
        // Arrange
        Long expectedId = 1L;
        String expectedName = "Goal";
        LocalDate expectedStartDate = LocalDate.of(2024, 4, 15);
        LocalDate expectedEndDate = LocalDate.of(2024, 5, 3);
        int expectedLives = 3;
        int expectedCurrentTile = 5;
        GoalDifficulty expectedGoalDifficulty = GoalDifficulty.MEDIUM;
        double expectedAmount = 10000.0;
        double expectedProgress = 500.0;
        User expectedUser = SavingGoalUtility.createUserA();

        // Assert
        assertEquals(expectedId, goal.getId());
        assertEquals(expectedName, goal.getGoalName());
        assertEquals(expectedStartDate, goal.getStartDate());
        assertEquals(expectedEndDate, goal.getEndDate());
        assertEquals(expectedLives, goal.getLives());
        assertEquals(expectedCurrentTile, goal.getCurrentTile());
        assertEquals(expectedGoalDifficulty, goal.getDifficulty());
        assertEquals(expectedAmount, goal.getAmount());
        assertEquals(expectedProgress, goal.getProgress());
        assertEquals(expectedUser.getEmail(), goal.getUser().getEmail());
    }


    @DisplayName("JUnit test for the setter methods")
    @Test
    void Goal_SetGoal_ReturnsExpectedValues() {

        // Arrange
        String expectedName = "Second Goal";
        LocalDate expectedStartDate = LocalDate.of(2024, 4, 20);
        LocalDate expectedEndDate = LocalDate.of(2024, 5, 1);
        int expectedLives = 2;
        int expectedCurrentTile = 10;
        GoalDifficulty expectedGoalDifficulty = GoalDifficulty.EASY;
        double expectedAmount = 2000.0;
        double expectedProgress = 100.0;
        User expectedUser = SavingGoalUtility.createUserB();


        // Act
        goal.setGoalName(expectedName);
        goal.setStartDate(expectedStartDate);
        goal.setEndDate(expectedEndDate);
        goal.setLives(expectedLives);
        goal.setCurrentTile(expectedCurrentTile);
        goal.setDifficulty(expectedGoalDifficulty);
        goal.setAmount(expectedAmount);
        goal.setProgress(expectedProgress);
        goal.setUser(expectedUser);


        // Assert
        assertEquals(expectedName, goal.getGoalName());
        assertEquals(expectedStartDate, goal.getStartDate());
        assertEquals(expectedEndDate, goal.getEndDate());
        assertEquals(expectedLives, goal.getLives());
        assertEquals(expectedCurrentTile, goal.getCurrentTile());
        assertEquals(expectedGoalDifficulty, goal.getDifficulty());
        assertEquals(expectedAmount, goal.getAmount());
        assertEquals(expectedProgress, goal.getProgress());
        assertEquals(expectedUser.getEmail(), goal.getUser().getEmail());

    }

    @DisplayName("JUnit test for the setters expecting exceptions setting Null")
    @Test
    public void Goal_SetGoalWithNull_ThrowsException() {
        // Act and Assert
        assertThrows(NullPointerException.class, () -> goal.setUser(null));
        assertThrows(NullPointerException.class, () -> goal.setGoalName(null));
        assertThrows(NullPointerException.class, () -> goal.setDifficulty(null));
        assertThrows(NullPointerException.class, () -> goal.setStartDate(null));
        assertThrows(NullPointerException.class, () -> goal.setEndDate(null));
    }

}