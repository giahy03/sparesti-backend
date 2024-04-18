package edu.ntnu.idatt2106.sparesti.model.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the SavingChallenge class.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
class SavingChallengeTest {

  SavingChallenge challenge;

  @BeforeEach
  void setUp() {
    challenge = ChallengeUtility.createSavingChallenge();
  }

  @Test
  void Challenge_NoArgsConstructor_ReturnChallenge() {
    //Arrange
    SavingChallenge challenge = new SavingChallenge();

    //Assert
    assertNotNull(challenge);
  }

  @Test
  void Challenge_GetChallenge_ReturnsExpectedValues() {
    //Arrange
    Long expectedId = 1L;
    String expectedTitle = "Challenge";
    LocalDate expectedStartDate = LocalDate.parse("2021-10-10");
    LocalDate expectedEndDate = LocalDate.parse("2021-10-20");
    int expectedTotalAmount = 100;
    int expectedCurrentAmount = 0;
    boolean expectedIsComplete = false;
    Difficulty expectedDifficulty = Difficulty.EASY;
    int expectedLives = 3;
    int expectedCurrentTiles = 0;
    User user = ChallengeUtility.createUserA();

    //Assert
    assertEquals(expectedId, challenge.getId());
    assertEquals(expectedTitle, challenge.getTitle());
    assertEquals(expectedStartDate, challenge.getStartDate());
    assertEquals(expectedEndDate, challenge.getEndDate());
    assertEquals(expectedIsComplete, challenge.isCompleted());
    assertEquals(expectedTotalAmount, challenge.getTotalAmount());
    assertEquals(expectedCurrentAmount, challenge.getCurrentAmount());
    assertEquals(expectedDifficulty, challenge.getDifficulty());
    assertEquals(expectedLives, challenge.getLives());
    assertEquals(expectedCurrentTiles, challenge.getCurrentTile());
    assertEquals(user.getEmail(), challenge.getUser().getEmail());
  }

  @Test
  void Challenge_SetChallenge_ReturnsExpectedValues() {

    //Arrange
    String expectedTitle = "Challengiada";
    LocalDate expectedStartDate = LocalDate.parse("2022-12-12");
    LocalDate expectedEndDate = LocalDate.parse("2023-12-12");
    int expectedTotalAmount = 40;
    int expectedCurrentAmount = 30;
    boolean expectedIsComplete = true;
    Difficulty expectedDifficulty = Difficulty.EASY;
    int expectedLives = 2;
    int expectedCurrentTiles = 5;
    User expectedUser = ChallengeUtility.createUserB();

    //Act
    challenge.setTitle(expectedTitle);
    challenge.setStartDate(expectedStartDate);
    challenge.setEndDate(expectedEndDate);
    challenge.setTotalAmount(expectedTotalAmount);
    challenge.setCurrentAmount(expectedCurrentAmount);
    challenge.setCompleted(expectedIsComplete);
    challenge.setDifficulty(expectedDifficulty);
    challenge.setLives(expectedLives);
    challenge.setCurrentTile(expectedCurrentTiles);


    //Assert
    assertEquals(expectedTitle, challenge.getTitle());
    assertEquals(expectedStartDate, challenge.getStartDate());
    assertEquals(expectedEndDate, challenge.getEndDate());
    assertEquals(expectedIsComplete, challenge.isCompleted());
    assertEquals(expectedTotalAmount, challenge.getTotalAmount());
    assertEquals(expectedCurrentAmount, challenge.getCurrentAmount());
    assertEquals(expectedDifficulty, challenge.getDifficulty());
    assertEquals(expectedLives, challenge.getLives());
    assertEquals(expectedCurrentTiles, challenge.getCurrentTile());
  }

}