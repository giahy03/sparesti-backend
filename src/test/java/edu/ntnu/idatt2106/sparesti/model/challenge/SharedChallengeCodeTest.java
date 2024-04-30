package edu.ntnu.idatt2106.sparesti.model.challenge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the SharedChallengeCode class.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
class SharedChallengeCodeTest {

  @Test
  @DisplayName("ChallengeCode NoArgsConstructor Return ChallengeCode")
  void ChallengeCode_NoArgsConstructor_ReturnChallengeCode() {
    //Arrange
    long expectedId = 1L;
    String expectedCode = "Code";
    List<SharedChallenge> expectedChallenges = List.of();

    //Act
    SharedChallengeCode challengeCode = new SharedChallengeCode();
    challengeCode.setSharedChallenges(expectedChallenges);

    //Assert
    assertTrue(challengeCode.getSharedChallenges().isEmpty());
  }

  @Test
  @DisplayName("ChallengeCode AllArgsConstructor Returns Expected Values")
  void ChallengeCode_AllArgsConstructor_ReturnsExpectedValues() {
    //Arrange
    long expectedId = 1L;
    String expectedCode = "Code";
    List<SharedChallenge> expectedChallenges = List.of();

    //Act
    SharedChallengeCode challengeCode = SharedChallengeCode.builder()
            .id(expectedId)
            .joinCode(expectedCode)
            .sharedChallenges(expectedChallenges)
            .build();

    //Assert
    assertEquals(expectedId, challengeCode.getId());
    assertEquals(expectedCode, challengeCode.getJoinCode());
    assertTrue(challengeCode.getSharedChallenges().isEmpty());
  }

}