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
class SharedChallengeTest {

  SharedChallenge challenge;

  @BeforeEach
  void setUp() {
    challenge = ChallengeUtility.createSharedChallengeA();
  }

  @Test
  void Challenge_NoArgsConstructor_ReturnChallenge() {
    //Arrange
    SharedChallenge challenge = new SharedChallenge();
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
    Difficulty expectedDifficulty = Difficulty.EASY;
    User user = ChallengeUtility.createUserA();

    //Assert
    assertEquals(expectedId, challenge.getId());
    assertEquals(expectedTitle, challenge.getTitle());
    assertEquals(expectedStartDate, challenge.getStartDate());
    assertEquals(expectedEndDate, challenge.getEndDate());
    assertEquals(expectedDifficulty, challenge.getDifficulty());
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
    Difficulty expectedDifficulty = Difficulty.EASY;
    int expectedLives = 2;
    int expectedCurrentTiles = 5;
    User expectedUser = ChallengeUtility.createUserB();

    //Act
    challenge.setTitle(expectedTitle);
    challenge.setStartDate(expectedStartDate);
    challenge.setEndDate(expectedEndDate);
    challenge.setDifficulty(expectedDifficulty);


    //Assert
    assertEquals(expectedTitle, challenge.getTitle());
    assertEquals(expectedStartDate, challenge.getStartDate());
    assertEquals(expectedEndDate, challenge.getEndDate());
  }

  @Test
  void Challenge_SetChallengeWithNull_ThrowsException() {
    //Act & Assert
    assertThrows(NullPointerException.class, () -> challenge.setTitle(null));
    assertThrows(NullPointerException.class, () -> challenge.setStartDate(null));
    assertThrows(NullPointerException.class, () -> challenge.setEndDate(null));
    assertThrows(NullPointerException.class, () -> challenge.setDifficulty(null));
  }






}