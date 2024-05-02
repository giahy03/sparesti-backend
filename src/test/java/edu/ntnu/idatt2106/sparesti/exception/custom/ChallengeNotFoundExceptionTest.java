package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.challenge.ChallengeNotFoundException;
import org.junit.jupiter.api.Test;

class ChallengeNotFoundExceptionTest {

  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new ChallengeNotFoundException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "Challenge not found.";

    // Act
    Exception exception = new ChallengeNotFoundException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
