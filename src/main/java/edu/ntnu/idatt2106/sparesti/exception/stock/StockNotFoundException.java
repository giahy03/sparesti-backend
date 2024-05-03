package edu.ntnu.idatt2106.sparesti.exception.stock;

/**
 * The class represents a custom exception for when a stock is not found.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class StockNotFoundException extends RuntimeException {

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public StockNotFoundException() {
    super("Stock not found.");
  }

  /**
   * Constructs an instance of the class with the specified error message.
   *
   * @param message The error message.
   */
  public StockNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the class with the specified error message and cause.
   *
   * @param message The error message.
   * @param cause The course of the exception.
   */
  public StockNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
