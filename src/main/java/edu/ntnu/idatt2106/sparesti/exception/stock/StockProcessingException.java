package edu.ntnu.idatt2106.sparesti.exception.stock;

/**
 * The class represents a custom exception for errors encountered during stock data processing.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class StockProcessingException extends RuntimeException {

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public StockProcessingException() {
    super("Failed to process stock data.");
  }

  /**
   * Constructs an instance of the class with the specified error message.
   *
   * @param message The error message.
   */
  public StockProcessingException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the class with the specified error message and cause.
   *
   * @param message The error message.
   * @param cause The course of the exception.
   */
  public StockProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}
