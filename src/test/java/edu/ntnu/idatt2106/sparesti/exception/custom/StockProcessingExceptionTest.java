package edu.ntnu.idatt2106.sparesti.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.exception.stock.StockProcessingException;
import org.junit.jupiter.api.Test;

class StockProcessingExceptionTest {
  @Test
  void testConstructorWithMessage() {
    // Arrange
    String message = "Test message";

    // Act
    Exception exception = new StockProcessingException(message);

    // Assert
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithoutMessage() {
    //Arrange
    String expectedMessage = "Failed to process stock data.";

    // Act
    Exception exception = new StockProcessingException();

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
