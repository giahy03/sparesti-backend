package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.analysis.ExternalApiException;
import org.junit.jupiter.api.Test;

class ExternalApiExceptionTest {

  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    ExternalApiException exception = new ExternalApiException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "External API error.";

    // Act
    ExternalApiException exception = new ExternalApiException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }

}
