package edu.ntnu.idatt2106.sparesti.exception.auth;


/**
 * The class represents a custom exception indicating unauthorized access to an operation.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class UnauthorizedOperationException extends RuntimeException {

  /**
   * Constructs an instance of the class with the specified error message.
   *
   * @param message The error message.
   */
  public UnauthorizedOperationException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public UnauthorizedOperationException() {
    super("Unauthorized access to perform this operation.");
  }
}
