package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.email.VerificationCodeExpiredException;
import org.junit.jupiter.api.Test;

/**
 * Test class for the VerificationCodeExpiredException.
 *
 * @author Tobias Oftedal
 */
class VerificationCodeExpiredExceptionTest {
  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new VerificationCodeExpiredException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "Verification code has expired.";

    // Act
    Exception exception = new VerificationCodeExpiredException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
