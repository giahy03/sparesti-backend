package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * Test class for the UserNotFoundException.
 *
 * @author Tobias Oftedal
 */
class UserNotFoundExceptionTest {
  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new UserNotFoundException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "User not found.";

    // Act
    Exception exception = new UserNotFoundException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
