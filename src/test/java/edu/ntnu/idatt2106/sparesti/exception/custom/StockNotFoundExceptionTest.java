package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.stock.StockNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * Test class for the StockNotFoundException.
 *
 * @author Tobias Oftedal
 */
class StockNotFoundExceptionTest {
  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new StockNotFoundException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "Stock not found.";

    // Act
    Exception exception = new StockNotFoundException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
