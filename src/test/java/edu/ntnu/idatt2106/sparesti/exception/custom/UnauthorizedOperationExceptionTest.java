package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.exception.stock.StockProcessingException;
import org.junit.jupiter.api.Test;

/**
 * Test class for the UnauthorizedOperationException.
 *
 * @author Tobias Oftedal
 */
class UnauthorizedOperationExceptionTest {
  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new UnauthorizedOperationException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "Unauthorized access to perform this operation.";

    // Act
    Exception exception = new UnauthorizedOperationException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
