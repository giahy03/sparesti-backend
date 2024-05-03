package edu.ntnu.idatt2106.sparesti.exception.user;

/**
 * The class represents a custom exception to indicate that a user does not exist.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Constructs an instance of the class with the specified error message.
   *
   * @param message The error message.
   */
  public UserNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public UserNotFoundException() {
    super("User not found.");
  }
}
