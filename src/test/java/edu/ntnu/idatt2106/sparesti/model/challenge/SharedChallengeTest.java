package edu.ntnu.idatt2106.sparesti.model.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("Challenge NoArgsConstructor Return Challenge Object")
  void Challenge_NoArgsConstructor_ReturnChallenge() {
    //Arrange
    SharedChallenge challenge = new SharedChallenge();
    //Assert
    assertNotNull(challenge);
  }

  @Test
  @DisplayName("Challenge AllArgsConstructor Returns Expected Values")
  void Challenge_AllArgsConstructor_ReturnsExpectedValues() {
    //Arrange
    Long expectedId = 1L;
    String expectedTitle = "Challenge";
    LocalDate expectedStartDate = LocalDate.parse("2021-10-10");
    LocalDate expectedEndDate = LocalDate.parse("2021-10-20");
    Difficulty expectedDifficulty = Difficulty.EASY;
    User user = ChallengeUtility.createUserA();
    Progress expectedProgress = Progress.NOT_STARTED;
    String expectedDescription = "Description";
    SharedChallengeCode expectedCode = ChallengeUtility.createSharedChallengeCodeA();

    //Act
    SharedChallenge challenge = SharedChallenge.builder()
            .id(expectedId)
            .title(expectedTitle)
            .startDate(expectedStartDate)
            .endDate(expectedEndDate)
            .difficulty(expectedDifficulty)
            .user(user)
            .progress(expectedProgress)
            .description(expectedDescription)
            .sharedChallengeCode(expectedCode)
            .build();

    //Assert
    assertEquals(expectedId, challenge.getId());
    assertEquals(expectedTitle, challenge.getTitle());
    assertEquals(expectedStartDate, challenge.getStartDate());
    assertEquals(expectedEndDate, challenge.getEndDate());
    assertEquals(expectedDifficulty, challenge.getDifficulty());
    assertEquals(user.getEmail(), challenge.getUser().getEmail());
    assertEquals(expectedProgress, challenge.getProgress());
    assertEquals(challenge.getSharedChallengeCode(), expectedCode);
    assertEquals(expectedDescription, challenge.getDescription());
  }

  @Test
  @DisplayName("Challenge SetChallenge Returns Expected Values")
  void Challenge_SetChallenge_ReturnsExpectedValues() {
    //Arrange
    String expectedTitle = "newChallenge";
    LocalDate expectedStartDate = LocalDate.parse("2022-12-12");
    LocalDate expectedEndDate = LocalDate.parse("2023-12-12");
    Difficulty expectedDifficulty = Difficulty.EASY;
    User expectedUser = ChallengeUtility.createUserB();
    Progress expectedProgress = Progress.COMPLETED;
    String expectedDescription = "Description";
    SharedChallengeCode expectedCode = ChallengeUtility.createSharedChallengeCodeB();

    //Act
    challenge.setTitle(expectedTitle);
    challenge.setStartDate(expectedStartDate);
    challenge.setEndDate(expectedEndDate);
    challenge.setDifficulty(expectedDifficulty);
    challenge.setUser(expectedUser);
    challenge.setProgress(expectedProgress);
    challenge.setDescription(expectedDescription);
    challenge.setSharedChallengeCode(expectedCode);

    //Assert
    assertEquals(expectedTitle, challenge.getTitle());
    assertEquals(expectedStartDate, challenge.getStartDate());
    assertEquals(expectedEndDate, challenge.getEndDate());
    assertEquals(expectedDifficulty, challenge.getDifficulty());
    assertEquals(expectedUser.getEmail(), challenge.getUser().getEmail());
    assertEquals(expectedProgress, challenge.getProgress());
    assertEquals(expectedDescription, challenge.getDescription());
    assertEquals(expectedCode, challenge.getSharedChallengeCode());
  }

  @Test
  @DisplayName("Challenge Set With Null Values Throws Exception")
  void Challenge_SetChallengeWithNull_ThrowsException() {
    //Act & Assert
    assertThrows(NullPointerException.class, () -> challenge.setTitle(null));
    assertThrows(NullPointerException.class, () -> challenge.setStartDate(null));
    assertThrows(NullPointerException.class, () -> challenge.setEndDate(null));
    assertThrows(NullPointerException.class, () -> challenge.setDifficulty(null));
  }

}