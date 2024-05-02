package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.challenge.ChallengeNotFoundException;
import edu.ntnu.idatt2106.sparesti.exception.email.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;

class EmailAlreadyExistsExceptionTest {

  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new EmailAlreadyExistsException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "Email already in use by another user.";

    // Act
    Exception exception = new EmailAlreadyExistsException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
